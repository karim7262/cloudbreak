package com.sequenceiq.periscope.monitor.handler;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status.DELETE_COMPLETED;
import static com.sequenceiq.periscope.api.model.ClusterState.RUNNING;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.StackStatusV4Response;
import com.sequenceiq.periscope.api.model.ClusterState;
import com.sequenceiq.periscope.domain.Cluster;
import com.sequenceiq.periscope.monitor.event.ClusterStatusSyncEvent;
import com.sequenceiq.periscope.service.AltusMachineUserService;
import com.sequenceiq.periscope.service.ClusterService;
import com.sequenceiq.periscope.utils.LoggingUtils;

@Component
public class ClusterStatusSyncHandler implements ApplicationListener<ClusterStatusSyncEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterStatusSyncHandler.class);

    @Inject
    private ClusterService clusterService;

    @Inject
    private CloudbreakCommunicator cloudbreakCommunicator;

    @Inject
    private AltusMachineUserService altusMachineUserService;

    @Override
    public void onApplicationEvent(ClusterStatusSyncEvent event) {
        long autoscaleClusterId = event.getClusterId();
        Cluster cluster = clusterService.findById(autoscaleClusterId);
        if (cluster == null) {
            return;
        }
        LoggingUtils.buildMdcContext(cluster);

        StackStatusV4Response statusResponse = cloudbreakCommunicator.getStackStatusByCrn(cluster.getStackCrn());
        boolean clusterAvailable = Optional.ofNullable(statusResponse.getStatus()).map(Status::isAvailable).orElse(false)
                && Optional.ofNullable(statusResponse.getClusterStatus()).map(Status::isAvailable).orElse(false);
        LOGGER.debug("Analysing CBCluster Status '{}' for Cluster '{}' ", statusResponse, cluster.getStackCrn());

        if (DELETE_COMPLETED.equals(statusResponse.getStatus())) {
            beforeDeleteCleanup(cluster);
            clusterService.removeById(autoscaleClusterId);
            LOGGER.info("Deleted cluster '{}', CB Stack Status '{}'.", cluster.getStackCrn(), statusResponse.getStatus());
        } else if (clusterAvailable && !RUNNING.equals(cluster.getState())) {
            clusterService.setState(cluster.getId(), ClusterState.RUNNING);
            LOGGER.info("Updated cluster '{}' to Running, CB Stack Status '{}', CB Cluster Status '{}'.",
                    cluster.getStackCrn(), statusResponse.getStatus(), statusResponse.getClusterStatus());
        } else if (!clusterAvailable && RUNNING.equals(cluster.getState())) {
            clusterService.setState(cluster.getId(), ClusterState.SUSPENDED);
            LOGGER.info("Suspended cluster '{}', CB Stack Status '{}', CB Cluster Status '{}'",
                    cluster.getStackCrn(), statusResponse.getStatus(), statusResponse.getClusterStatus());
        } else if (cluster.getEnvironmentCrn() == null) {
            String environmentCrn = cloudbreakCommunicator.getAutoscaleClusterByCrn(cluster.getStackCrn()).getEnvironmentCrn();
            clusterService.setEnvironmentCrn(cluster.getId(), environmentCrn);
        } else if (cluster.getMachineUserCrn() == null) {
            altusMachineUserService.initializeMachineUserForEnvironment(cluster);
        }
    }

    protected void beforeDeleteCleanup(Cluster cluster) {
        try {
            if ((cluster.getEnvironmentCrn() != null) && (clusterService.countByEnvironmentCrn(cluster.getEnvironmentCrn()) <= 1)) {
                altusMachineUserService.deleteMachineUserForEnvironment(cluster.getClusterPertain().getTenant(),
                        cluster.getMachineUserCrn(), cluster.getEnvironmentCrn());
            }
        } catch (Exception ex) {
            LOGGER.warn("Error deleting machineUserCrn '{}' for environment '{}'",
                    cluster.getMachineUserCrn(), cluster.getEnvironmentCrn(), ex);
        }
    }
}

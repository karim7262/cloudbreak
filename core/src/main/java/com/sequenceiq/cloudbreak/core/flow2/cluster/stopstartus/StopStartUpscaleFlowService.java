package com.sequenceiq.cloudbreak.core.flow2.cluster.stopstartus;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status.AVAILABLE;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status.UPDATE_FAILED;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status.UPDATE_IN_PROGRESS;
import static com.sequenceiq.cloudbreak.event.ResourceEvent.CLUSTER_SCALING_FAILED;
import static com.sequenceiq.cloudbreak.event.ResourceEvent.CLUSTER_SCALING_STOPSTART_UPSCALE_COMMISSIONING;
import static com.sequenceiq.cloudbreak.event.ResourceEvent.CLUSTER_SCALING_STOPSTART_UPSCALE_FINISHED;
import static com.sequenceiq.cloudbreak.event.ResourceEvent.CLUSTER_SCALING_STOPSTART_UPSCALE_INIT;
import static com.sequenceiq.cloudbreak.event.ResourceEvent.CLUSTER_SCALING_STOPSTART_UPSCALE_NODES_STARTED;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DetailedStackStatus;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceStatus;
import com.sequenceiq.cloudbreak.core.flow2.stack.CloudbreakFlowMessageService;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostGroup;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceMetaData;
import com.sequenceiq.cloudbreak.domain.view.StackView;
import com.sequenceiq.cloudbreak.service.StackUpdater;
import com.sequenceiq.cloudbreak.service.cluster.ClusterService;
import com.sequenceiq.cloudbreak.service.hostgroup.HostGroupService;
import com.sequenceiq.cloudbreak.service.stack.InstanceMetaDataService;
import com.sequenceiq.cloudbreak.service.stack.flow.MetadataSetupService;

@Component
class StopStartUpscaleFlowService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopStartUpscaleFlowService.class);

    @Inject
    private ClusterService clusterService;

    @Inject
    private CloudbreakFlowMessageService flowMessageService;

    @Inject
    private StackUpdater stackUpdater;

    @Inject
    private HostGroupService hostGroupService;

    @Inject
    private InstanceMetaDataService instanceMetaDataService;

    @Inject
    private MetadataSetupService metadataSetupService;

    // TODO CB-14929: General state update, DB update handling needs to be fixed.
    //  What inside vs outside a transaction. Forcing a persist?, etc

    void startingInstances(long stackId, String hostGroupName, int nodeCount) {
        clusterService.updateClusterStatusByStackId(stackId, DetailedStackStatus.UPSCALE_BY_START_IN_PROGRESS,
                String.format("Scaling up (stopstart) host  group: %s", hostGroupName));
        flowMessageService.fireEventAndLog(stackId, UPDATE_IN_PROGRESS.name(), CLUSTER_SCALING_STOPSTART_UPSCALE_INIT, hostGroupName, String.valueOf(nodeCount));
    }

    void instancesStarted(StopStartUpscaleContext context, long stackId, List<InstanceMetaData> instancesStarted) {
        Stack stack = context.getStack();
        instancesStarted.stream().forEach(x -> instanceMetaDataService.updateInstanceStatus(x, InstanceStatus.STARTED));
        stackUpdater.updateStackStatus(stack.getId(), DetailedStackStatus.STARTING_CLUSTER_MANAGER_SERVICES,
                "Instances: " + instancesStarted.size() + " started successfully.");
        flowMessageService.fireEventAndLog(stack.getId(), AVAILABLE.name(), CLUSTER_SCALING_STOPSTART_UPSCALE_NODES_STARTED,
                String.valueOf(instancesStarted.size()), instancesStarted.stream().map(x -> x.getInstanceId()).collect(Collectors.toList()).toString());
    }

    void upscaleCommissionNewNodes(long stackId, String hostGroupName, List<String> instanceIds) {
        // TODO CB-14929: Update instance state to SERVICES_RUNNING? (or introduce a new state) at this point, rather than when the instances start up
        clusterService.updateClusterStatusByStackId(stackId, DetailedStackStatus.STARTING_CLUSTER_MANAGER_SERVICES,
                String.format("Commissioning via CM: %s", hostGroupName));
        flowMessageService.fireEventAndLog(stackId, UPDATE_IN_PROGRESS.name(), CLUSTER_SCALING_STOPSTART_UPSCALE_COMMISSIONING,
                hostGroupName, String.valueOf(instanceIds.size()), instanceIds.toString());
    }

    void clusterUpscaleFinished(StackView stackView, String hostgroupName, List<InstanceMetaData> commissioned) {
        int numOfFailedHosts = updateMetadata(stackView, hostgroupName);
        boolean success = numOfFailedHosts == 0;
        if (success) {
            LOGGER.debug("stopstart upscaled successfully");
            commissioned.stream().forEach(x -> instanceMetaDataService.updateInstanceStatus(x, InstanceStatus.SERVICES_HEALTHY));
            stackUpdater.updateStackStatus(stackView.getId(), DetailedStackStatus.AVAILABLE, String.format("finished starting nodes"));
            flowMessageService.fireEventAndLog(stackView.getId(), AVAILABLE.name(), CLUSTER_SCALING_STOPSTART_UPSCALE_FINISHED, hostgroupName);
        } else {
            LOGGER.debug("stopstart upscale failed. {} hosts failed to upscale", numOfFailedHosts);
            clusterService.updateClusterStatusByStackId(stackView.getId(), DetailedStackStatus.UPSCALE_BY_START_FAILED);
            flowMessageService.fireEventAndLog(stackView.getId(), UPDATE_FAILED.name(), CLUSTER_SCALING_FAILED, "added to",
                    String.format("Cluster upscale operation failed on %d node(s).", numOfFailedHosts));
        }
    }

    void clusterUpscaleFailed(long stackId, Exception errorDetails) {
        LOGGER.info("Error during stopstart upscale flow: " + errorDetails.getMessage(), errorDetails);
        clusterService.updateClusterStatusByStackId(stackId, DetailedStackStatus.UPSCALE_BY_START_FAILED, errorDetails.getMessage());
        stackUpdater.updateStackStatus(stackId, DetailedStackStatus.UPSCALE_BY_START_FAILED,
                String.format("New node(s) could not be added to the cluster: %s", errorDetails));
        flowMessageService.fireEventAndLog(stackId, UPDATE_FAILED.name(), CLUSTER_SCALING_FAILED, "added to", errorDetails.getMessage());
    }

    private int updateMetadata(StackView stackView, String hostGroupName) {
        LOGGER.info("Start update metadata");
        Optional<HostGroup> hostGroupOptional = hostGroupService.getByClusterIdAndName(stackView.getClusterView().getId(), hostGroupName);
        if (hostGroupOptional.isPresent()) {
            Set<InstanceMetaData> notDeletedInstanceMetaDataSet = hostGroupOptional.get().getInstanceGroup().getNotDeletedInstanceMetaDataSet();
            return updateFailedHostMetaData(notDeletedInstanceMetaDataSet);
        } else {
            return 0;
        }
    }

    private int updateFailedHostMetaData(Collection<InstanceMetaData> instanceMetaData) {
        List<String> upscaleHostNames = getHostNames(instanceMetaData);
        Collection<String> successHosts = new HashSet<>(upscaleHostNames);
        return updateFailedHostMetaData(successHosts, instanceMetaData);
    }

    private int updateFailedHostMetaData(Collection<String> successHosts, Iterable<InstanceMetaData> instanceMetaDatas) {
        int failedHosts = 0;
        for (InstanceMetaData metaData : instanceMetaDatas) {
            if (!successHosts.contains(metaData.getDiscoveryFQDN())) {
                instanceMetaDataService.updateInstanceStatus(metaData, InstanceStatus.ORCHESTRATION_FAILED,
                        "Cluster upscale failed. Host does not have fqdn.");
                failedHosts++;
            }
        }
        return failedHosts;
    }

    private List<String> getHostNames(Collection<InstanceMetaData> instanceMetaData) {
        return instanceMetaData.stream().map(InstanceMetaData::getDiscoveryFQDN).collect(Collectors.toList());
    }
}

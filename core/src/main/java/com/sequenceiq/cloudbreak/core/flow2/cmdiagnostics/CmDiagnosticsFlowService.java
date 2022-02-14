package com.sequenceiq.cloudbreak.core.flow2.cmdiagnostics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.core.bootstrap.service.ClusterDeletionBasedExitCriteriaModel;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceMetaData;
import com.sequenceiq.cloudbreak.orchestrator.exception.CloudbreakOrchestratorFailedException;
import com.sequenceiq.cloudbreak.orchestrator.host.TelemetryOrchestrator;
import com.sequenceiq.cloudbreak.orchestrator.model.GatewayConfig;
import com.sequenceiq.cloudbreak.orchestrator.model.Node;
import com.sequenceiq.cloudbreak.service.GatewayConfigService;
import com.sequenceiq.cloudbreak.service.stack.InstanceMetaDataService;
import com.sequenceiq.cloudbreak.service.stack.StackService;
import com.sequenceiq.cloudbreak.orchestrator.metadata.OrchestratorMetadataFilter;

@Service
public class CmDiagnosticsFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmDiagnosticsFlowService.class);

    @Inject
    private StackService stackService;

    @Inject
    private InstanceMetaDataService instanceMetaDataService;

    @Inject
    private GatewayConfigService gatewayConfigService;

    @Inject
    private TelemetryOrchestrator telemetryOrchestrator;

    public void init(Long stackId, Map<String, Object> parameters, Set<String> excludeHosts) throws CloudbreakOrchestratorFailedException {
        executeCmDiagnosticOperation(stackId, "init", parameters, excludeHosts,
                (gatewayConfigs, nodes, exitModel) -> telemetryOrchestrator.initDiagnosticCollection(gatewayConfigs, nodes, parameters, exitModel));
    }

    public void upload(Long stackId, Map<String, Object> parameters, Set<String> excludeHosts) throws CloudbreakOrchestratorFailedException {
        executeCmDiagnosticOperation(stackId, "upload", parameters, excludeHosts,
                (gatewayConfigs, nodes, exitModel) -> telemetryOrchestrator.uploadCollectedDiagnostics(gatewayConfigs, nodes, parameters, exitModel));
    }

    public void cleanup(Long stackId, Map<String, Object> parameters, Set<String> excludeHosts) throws CloudbreakOrchestratorFailedException {
        executeCmDiagnosticOperation(stackId, "cleanup", parameters, excludeHosts,
                (gatewayConfigs, nodes, exitModel) -> telemetryOrchestrator.cleanupCollectedDiagnostics(gatewayConfigs, nodes, parameters, exitModel));
    }

    private void executeCmDiagnosticOperation(Long stackId, String operation, Map<String, Object> parameters, Set<String> excludeHosts,
            CmDiagnosticsFlowOperation func) throws CloudbreakOrchestratorFailedException {
        LOGGER.debug("Diagnostics {} will be called only on the primary gateway address", operation);
        Stack stack = stackService.getByIdWithListsInTransaction(stackId);
        Set<InstanceMetaData> instanceMetaDataSet = instanceMetaDataService.findNotTerminatedForStack(stackId);
        List<GatewayConfig> gatewayConfigs = gatewayConfigService.getAllGatewayConfigs(stack);
        String primaryGatewayIp = gatewayConfigService.getPrimaryGatewayIp(stack);
        Set<String> hosts = new HashSet<>(Arrays.asList(primaryGatewayIp));
        OrchestratorMetadataFilter filter = OrchestratorMetadataFilter.Builder.newBuilder()
                .includeHosts(hosts)
                .exlcudeHosts(excludeHosts)
                .build();
        Set<Node> filteredNodes = filter.apply(getNodes(instanceMetaDataSet));
        ClusterDeletionBasedExitCriteriaModel exitModel = new ClusterDeletionBasedExitCriteriaModel(stackId, stack.getCluster().getId());
        if (filteredNodes.isEmpty()) {
            LOGGER.debug("Diagnostics {} has been skipped. (no target minions)", operation);
        } else {
            func.apply(gatewayConfigs, filteredNodes, exitModel);
        }
    }

    private Set<Node> getNodes(Set<InstanceMetaData> instanceMetaDataSet) {
        return instanceMetaDataSet.stream()
                .map(im -> new Node(im.getPrivateIp(), im.getPublicIp(), im.getInstanceId(),
                        im.getInstanceGroup().getTemplate().getInstanceType(), im.getDiscoveryFQDN(), im.getInstanceGroup().getGroupName()))
                .collect(Collectors.toSet());
    }
}

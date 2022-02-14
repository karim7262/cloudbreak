package com.sequenceiq.cloudbreak.core.flow2.cmdiagnostics;

import java.util.List;
import java.util.Set;

import com.sequenceiq.cloudbreak.core.bootstrap.service.ClusterDeletionBasedExitCriteriaModel;
import com.sequenceiq.cloudbreak.orchestrator.exception.CloudbreakOrchestratorFailedException;
import com.sequenceiq.cloudbreak.orchestrator.model.GatewayConfig;
import com.sequenceiq.cloudbreak.orchestrator.model.Node;

@FunctionalInterface
public interface CmDiagnosticsFlowOperation {

    void apply(List<GatewayConfig> gatewayConfigs, Set<Node> nodes, ClusterDeletionBasedExitCriteriaModel exitCriteriaModel)
            throws CloudbreakOrchestratorFailedException;
}

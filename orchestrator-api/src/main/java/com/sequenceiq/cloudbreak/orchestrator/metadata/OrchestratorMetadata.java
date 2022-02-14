package com.sequenceiq.cloudbreak.orchestrator.metadata;

import java.util.List;
import java.util.Set;

import com.sequenceiq.cloudbreak.orchestrator.model.GatewayConfig;
import com.sequenceiq.cloudbreak.orchestrator.model.Node;
import com.sequenceiq.cloudbreak.orchestrator.state.ExitCriteriaModel;

public class OrchestratorMetadata {

    private final List<GatewayConfig> gatewayConfigs;

    private final Set<Node> nodes;

    private final ExitCriteriaModel exitCriteriaModel;

    public OrchestratorMetadata(List<GatewayConfig> gatewayConfigs, Set<Node> nodes, ExitCriteriaModel exitCriteriaModel) {
        this.gatewayConfigs = gatewayConfigs;
        this.nodes = nodes;
        this.exitCriteriaModel = exitCriteriaModel;
    }

    public List<GatewayConfig> getGatewayConfigs() {
        return gatewayConfigs;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public ExitCriteriaModel getExitCriteriaModel() {
        return exitCriteriaModel;
    }
}

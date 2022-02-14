package com.sequenceiq.freeipa.service.orchestrator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.orchestrator.metadata.OrchestratorMetadata;
import com.sequenceiq.cloudbreak.orchestrator.metadata.OrchestratorMetadataProvider;
import com.sequenceiq.cloudbreak.orchestrator.model.GatewayConfig;
import com.sequenceiq.cloudbreak.orchestrator.model.Node;
import com.sequenceiq.freeipa.entity.InstanceMetaData;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.orchestrator.StackBasedExitCriteriaModel;
import com.sequenceiq.freeipa.service.GatewayConfigService;
import com.sequenceiq.freeipa.service.freeipa.flow.FreeIpaNodeUtilService;
import com.sequenceiq.freeipa.service.stack.StackService;
import com.sequenceiq.freeipa.service.stack.instance.InstanceMetaDataService;

@Service
public class OrchestratorService implements OrchestratorMetadataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrchestratorService.class);

    @Inject
    private StackService stackService;

    @Inject
    private InstanceMetaDataService instanceMetaDataService;

    @Inject
    private GatewayConfigService gatewayConfigService;

    @Inject
    private FreeIpaNodeUtilService freeIpaNodeUtilService;

    @Override
    public OrchestratorMetadata getOrchestratorMetadata(Long stackId) {
        Stack stack = stackService.getStackById(stackId);
        Set<InstanceMetaData> instanceMetaDataSet = instanceMetaDataService.findNotTerminatedForStack(stackId);
        Set<Node> nodes = freeIpaNodeUtilService.mapInstancesToNodes(instanceMetaDataSet);
        List<GatewayConfig> gatewayConfigs = gatewayConfigService.getNotDeletedGatewayConfigs(stack);
        StackBasedExitCriteriaModel exitCriteriaModel = new StackBasedExitCriteriaModel(stackId);
        return new OrchestratorMetadata(gatewayConfigs, nodes, exitCriteriaModel);
    }

    @Override
    public byte[] getStoredStates(Long stackId) {
        LOGGER.info("Salt states are not stored by freeipa service - skip obtaining state");
        return null;
    }

    @Override
    public void storeNewState(Long stackId, byte[] newFullSaltState) {
        LOGGER.info("Salt states are not stored by freeipa service - skip storing new state");
    }

    @Override
    public List<String> getSaltStateDefinitionBaseFolders() {
        return Arrays.asList("salt-common", "freeipa-salt");
    }

}

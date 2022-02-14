package com.sequenceiq.freeipa.service.orchestrator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.orchestrator.metadata.OrchestratorMetadata;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.service.GatewayConfigService;
import com.sequenceiq.freeipa.service.freeipa.flow.FreeIpaNodeUtilService;
import com.sequenceiq.freeipa.service.stack.StackService;
import com.sequenceiq.freeipa.service.stack.instance.InstanceMetaDataService;

@ExtendWith(MockitoExtension.class)
public class OrchestratorServiceTest {

    private static final Long STACK_ID = 1L;

    @InjectMocks
    private OrchestratorService underTest;

    @Mock
    private StackService stackService;

    @Mock
    private InstanceMetaDataService instanceMetaDataService;

    @Mock
    private GatewayConfigService gatewayConfigService;

    @Mock
    private FreeIpaNodeUtilService freeIpaNodeUtilService;

    @BeforeEach
    public void setUp() {
        underTest = new OrchestratorService();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrchestratorMetadata() {
        // GIVEN
        Stack stack = new Stack();
        stack.setId(STACK_ID);
        given(stackService.getStackById(STACK_ID)).willReturn(stack);
        given(instanceMetaDataService.findNotTerminatedForStack(STACK_ID)).willReturn(new HashSet<>());
        given(freeIpaNodeUtilService.mapInstancesToNodes(anySet())).willReturn(new HashSet<>());
        given(gatewayConfigService.getNotDeletedGatewayConfigs(any())).willReturn(new ArrayList<>());
        // WHEN
        OrchestratorMetadata result = underTest.getOrchestratorMetadata(STACK_ID);
        // THEN
        assertNotNull(result);
        verify(gatewayConfigService, times(1)).getNotDeletedGatewayConfigs(any());
    }

    @Test
    public void testGetStoredTelemetryStates() {
        // GIVEN
        // WHEN
        byte[] result = underTest.getStoredStates(STACK_ID);
        // THEN
        assertNull(result);
    }

    @Test
    public void testGetSaltStateDefinitionBaseFolders() {
        // GIVEN
        // WHEN
        List<String> result = underTest.getSaltStateDefinitionBaseFolders();
        // THEN
        assertTrue(result.contains("freeipa-salt"));
        assertTrue(result.contains("salt-common"));
    }
}

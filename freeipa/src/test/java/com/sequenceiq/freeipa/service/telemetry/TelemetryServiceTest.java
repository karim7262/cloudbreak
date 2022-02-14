package com.sequenceiq.freeipa.service.telemetry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.telemetry.TelemetryComponentType;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.service.stack.StackService;

@ExtendWith(MockitoExtension.class)
public class TelemetryServiceTest {

    private static final Long STACK_ID = 1L;

    @InjectMocks
    private TelemetryService underTest;

    @Mock
    private TelemetryConfigService telemetryConfigService;

    @Mock
    private StackService stackService;

    @BeforeEach
    public void setUp() {
        underTest = new TelemetryService();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTelemetryConfigs() throws Exception {
        // GIVEN
        Stack stack = new Stack();
        stack.setId(STACK_ID);
        given(stackService.getStackById(STACK_ID)).willReturn(stack);
        given(telemetryConfigService.createTelemetryPillarConfig(stack)).willReturn(new HashMap<>());
        // WHEN
        underTest.createTelemetryConfigs(STACK_ID, Set.of(TelemetryComponentType.CDP_TELEMETRY));
        // THEN
        verify(telemetryConfigService, times(1)).createTelemetryPillarConfig(any());
    }
}

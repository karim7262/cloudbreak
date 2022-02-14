package com.sequenceiq.freeipa.service.telemetry;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.orchestrator.exception.CloudbreakOrchestratorFailedException;
import com.sequenceiq.cloudbreak.orchestrator.model.SaltPillarProperties;
import com.sequenceiq.cloudbreak.telemetry.TelemetryComponentType;
import com.sequenceiq.cloudbreak.telemetry.orchestrator.TelemetryConfigProvider;
import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.service.stack.StackService;

@Service
public class TelemetryService implements TelemetryConfigProvider {

    @Inject
    private StackService stackService;

    @Inject
    private TelemetryConfigService telemetryConfigService;

    @Override
    public Map<String, SaltPillarProperties> createTelemetryConfigs(Long stackId, Set<TelemetryComponentType> components)
            throws CloudbreakOrchestratorFailedException {
        Stack stack = stackService.getStackById(stackId);
        return telemetryConfigService.createTelemetryPillarConfig(stack);
    }
}

package com.sequenceiq.freeipa.flow.freeipa.diagnostics.handler;

import static com.sequenceiq.freeipa.flow.freeipa.diagnostics.event.DiagnosticsCollectionHandlerSelectors.VM_PREFLIGHT_CHECK_DIAGNOSTICS_EVENT;
import static com.sequenceiq.freeipa.flow.freeipa.diagnostics.event.DiagnosticsCollectionStateSelectors.START_DIAGNOSTICS_ENSURE_MACHINE_USER_EVENT;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudera.thunderhead.service.common.usage.UsageProto;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.cloudbreak.telemetry.diagnostics.DiagnosticsOperationsService;
import com.sequenceiq.common.api.telemetry.model.DiagnosticsDestination;
import com.sequenceiq.common.model.diagnostics.DiagnosticParameters;
import com.sequenceiq.flow.reactor.api.handler.ExceptionCatcherEventHandler;
import com.sequenceiq.flow.reactor.api.handler.HandlerEvent;
import com.sequenceiq.freeipa.flow.freeipa.diagnostics.event.DiagnosticsCollectionEvent;
import com.sequenceiq.freeipa.flow.freeipa.diagnostics.event.DiagnosticsCollectionFailureEvent;

import reactor.bus.Event;

@Component
public class DiagnosticsVmPreFlightCheckHandler extends ExceptionCatcherEventHandler<DiagnosticsCollectionEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticsVmPreFlightCheckHandler.class);

    @Inject
    private DiagnosticsOperationsService diagnosticsOperationsService;

    @Override
    public Selectable doAccept(HandlerEvent<DiagnosticsCollectionEvent> event) throws Exception {
        DiagnosticsCollectionEvent data = event.getData();
        Long resourceId = data.getResourceId();
        String resourceCrn = data.getResourceCrn();
        DiagnosticParameters parameters = data.getParameters();
        Map<String, Object> parameterMap = parameters.toMap();
        LOGGER.debug("Diagnostics collection VM preflight check started. resourceCrn: '{}', parameters: '{}'", resourceCrn, parameterMap);
        if (!DiagnosticsDestination.ENG.equals(parameters.getDestination())) {
            diagnosticsOperationsService.vmPreflightCheck(resourceId, parameters);
        }
        return DiagnosticsCollectionEvent.builder()
                .withResourceCrn(resourceCrn)
                .withResourceId(resourceId)
                .withSelector(START_DIAGNOSTICS_ENSURE_MACHINE_USER_EVENT.selector())
                .withParameters(parameters)
                .build();
    }

    @Override
    protected Selectable defaultFailureEvent(Long resourceId, Exception e, Event<DiagnosticsCollectionEvent> event) {
        return new DiagnosticsCollectionFailureEvent(resourceId, e, event.getData().getResourceCrn(), event.getData().getParameters(),
                UsageProto.CDPVMDiagnosticsFailureType.Value.PREFLIGHT_VM_CHECK_FAILURE.name());
    }

    @Override
    public String selector() {
        return VM_PREFLIGHT_CHECK_DIAGNOSTICS_EVENT.selector();
    }
}

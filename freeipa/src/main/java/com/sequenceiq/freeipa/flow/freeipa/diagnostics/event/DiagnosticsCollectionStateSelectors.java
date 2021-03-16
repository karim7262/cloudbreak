package com.sequenceiq.freeipa.flow.freeipa.diagnostics.event;

import com.sequenceiq.flow.core.FlowEvent;

public enum DiagnosticsCollectionStateSelectors implements FlowEvent {
    START_DIAGNOSTICS_SALT_VALIDATION_EVENT,
    START_DIAGNOSTICS_INIT_EVENT,
    START_DIAGNOSTICS_ENSURE_MACHINE_USER_EVENT,
    START_DIAGNOSTICS_COLLECTION_EVENT,
    START_DIAGNOSTICS_UPLOAD_EVENT,
    START_DIAGNOSTICS_CLEANUP_EVENT,
    FINISH_DIAGNOSTICS_COLLECTION_EVENT,
    FINALIZE_DIAGNOSTICS_COLLECTION_EVENT,
    FAILED_DIAGNOSTICS_COLLECTION_EVENT,
    HANDLED_FAILED_DIAGNOSTICS_COLLECTION_EVENT;

    @Override
    public String event() {
        return name();
    }
}
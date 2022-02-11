package com.sequenceiq.datalake.flow.freeipa.upscale.event;

import com.sequenceiq.datalake.flow.SdxEvent;

public class FreeIpaUpscaleStartEvent extends SdxEvent {
    private final String envCrn;

    private String operationId;

    public FreeIpaUpscaleStartEvent(Long sdxId, String userId, String envCrn) {
        super(sdxId, userId);
        this.envCrn = envCrn;
    }

    public String getEnvCrn() {
        return envCrn;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}

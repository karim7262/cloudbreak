package com.sequenceiq.cloudbreak.job.stackpatcher.config;

import java.util.Map;

import com.sequenceiq.cloudbreak.domain.stack.StackPatchType;

public class ExistingStackPatcherConfigPatch {

    private StackPatchType type;

    private boolean enabled;

    private Map<String, String> configs;

    public StackPatchType getType() {
        return type;
    }

    public void setType(StackPatchType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }
}

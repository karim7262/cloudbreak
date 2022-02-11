package com.sequenceiq.cloudbreak.job.stackpatcher.config;

import java.util.Map;

import com.sequenceiq.cloudbreak.domain.stack.StackPatchType;

public abstract class StackPatchConfig {

    private final Map<String, String> config;

    public StackPatchConfig(ExistingStackPatcherConfig existingStackPatcherConfig, StackPatchType stackPatchType) {
        this.config = existingStackPatcherConfig.getPatches().stream()
                .filter(c -> c.getType().equals(stackPatchType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No config found for stack patch " + stackPatchType))
                .getConfigs();
    }

    protected String getConfigValue(String key) {
        return config.get(key);
    }
}

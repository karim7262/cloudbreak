package com.sequenceiq.cloudbreak.reactor.api.event.resource;

import java.util.Set;

import com.sequenceiq.cloudbreak.reactor.api.ClusterPlatformRequest;
import com.sequenceiq.cloudbreak.reactor.api.event.HostGroupPayload;

public abstract class AbstractClusterScaleRequest extends ClusterPlatformRequest implements HostGroupPayload {
    private final Set<String> hostGroupNames;

    protected AbstractClusterScaleRequest(Long stackId, Set<String> hostGroupNames) {
        super(stackId);
        this.hostGroupNames = hostGroupNames;
    }

    @Override
    public Set<String> getHostGroupNames() {
        return hostGroupNames;
    }
}

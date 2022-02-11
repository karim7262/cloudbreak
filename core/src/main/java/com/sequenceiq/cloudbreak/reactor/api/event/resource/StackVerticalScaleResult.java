package com.sequenceiq.cloudbreak.reactor.api.event.resource;

import java.util.ArrayList;
import java.util.List;

import com.sequenceiq.cloudbreak.cloud.event.CloudPlatformResult;
import com.sequenceiq.cloudbreak.cloud.model.CloudResourceStatus;
import com.sequenceiq.cloudbreak.cloud.model.ResourceStatus;

public class StackVerticalScaleResult extends CloudPlatformResult {

    private final ResourceStatus resourceStatus;

    private final List<CloudResourceStatus> results;

    public StackVerticalScaleResult(Long resourceId, ResourceStatus resourceStatus, List<CloudResourceStatus> results) {
        super(resourceId);
        this.resourceStatus = resourceStatus;
        this.results = results;
    }

    public StackVerticalScaleResult(String statusReason, Exception errorDetails, Long resourceId) {
        super(statusReason, errorDetails, resourceId);
        resourceStatus = ResourceStatus.FAILED;
        results = new ArrayList<>();
    }

    public List<CloudResourceStatus> getResults() {
        return results;
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public boolean isFailed() {
        return resourceStatus == ResourceStatus.FAILED;
    }

}

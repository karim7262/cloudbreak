package com.sequenceiq.cloudbreak.service.stackpatch;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudera.thunderhead.service.common.usage.UsageProto;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.StackPatchType;
import com.sequenceiq.cloudbreak.usage.UsageReporter;

@Service
public class StackPatchUsageReporterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackPatchUsageReporterService.class);

    @Inject
    private UsageReporter usageReporter;

    public void reportAffected(Stack stack, StackPatchType stackPatchType) {
        sendUsageReport(stack, stackPatchType, UsageProto.CDPStackPatchEventType.Value.AFFECTED);
    }

    public void reportSuccess(Stack stack, StackPatchType stackPatchType) {
        sendUsageReport(stack, stackPatchType, UsageProto.CDPStackPatchEventType.Value.SUCCESS);
    }

    public void reportFailure(Stack stack, StackPatchType stackPatchType, String failureMessage) {
        sendUsageReport(stack, stackPatchType, UsageProto.CDPStackPatchEventType.Value.FAILURE, failureMessage);
    }

    private void sendUsageReport(Stack stack, StackPatchType stackPatchType, UsageProto.CDPStackPatchEventType.Value eventType) {
        sendUsageReport(stack, stackPatchType, eventType, "");
    }

    private void sendUsageReport(Stack stack, StackPatchType stackPatchType, UsageProto.CDPStackPatchEventType.Value eventType, String message) {
        try {
            UsageProto.CDPStackPatchEvent cdpStackPatchEvent = UsageProto.CDPStackPatchEvent.newBuilder()
                    .setResourceCrn(stack.getResourceCrn())
                    .setStackPatchType(stackPatchType.name())
                    .setEventType(eventType)
                    .setMessage(message)
                    .build();
            usageReporter.cdpStackPatcherEvent(cdpStackPatchEvent);
        } catch (Exception e) {
            LOGGER.error("Failed to report stack patch event with resource crn {}, stack patch type {}, event type {} and message {}",
                    stack.getResourceCrn(), stackPatchType, eventType.name(), message, e);
        }
    }
}

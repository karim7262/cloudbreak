package com.sequenceiq.cloudbreak.structuredevent.service.telemetry.converter;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.cloudera.thunderhead.service.common.usage.UsageProto;
import com.sequenceiq.cloudbreak.structuredevent.event.cdp.environment.CDPEnvironmentStructuredFlowEvent;

class CDPStructuredFlowEventToCDPOperationDetailsConverterTest {

    private CDPStructuredFlowEventToCDPOperationDetailsConverter underTest;

    @BeforeEach()
    public void setUp() {
        underTest = new CDPStructuredFlowEventToCDPOperationDetailsConverter();
    }

    @Test
    public void testConvertWithNull() {
        Assert.assertNull("We should return with null if the input is null", underTest.convert(null));
    }

    @Test
    public void testConversionWithNullOperation() {
        CDPEnvironmentStructuredFlowEvent cdpStructuredFlowEvent = new CDPEnvironmentStructuredFlowEvent();
        Whitebox.setInternalState(underTest, "appVersion", "version-1234");

        UsageProto.CDPOperationDetails details = underTest.convert(cdpStructuredFlowEvent);

        Assert.assertEquals("", details.getAccountId());
        Assert.assertEquals("", details.getResourceCrn());
        Assert.assertEquals("", details.getResourceName());
        Assert.assertEquals("", details.getInitiatorCrn());

        Assert.assertEquals("version-1234", details.getApplicationVersion());
    }

}
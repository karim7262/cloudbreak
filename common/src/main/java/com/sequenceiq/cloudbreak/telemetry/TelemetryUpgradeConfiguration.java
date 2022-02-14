package com.sequenceiq.cloudbreak.telemetry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TelemetryUpgradeConfiguration {

    private final boolean enabled;

    private final String desiredCdpTelemetryVersion;

    private final String desiredCdpLoggingAgentVersion;

    private final String desiredMeteringAgentDate;

    public TelemetryUpgradeConfiguration(@Value("${telemetry.upgrade.enabled}") boolean enabled,
            @Value("${telemetry.upgrade.cdp-telemetry.desired-version}") String desiredCdpTelemetryVersion,
            @Value("${telemetry.upgrade.cdp-logging-agent.desired-version}") String desiredCdpLoggingAgentVersion,
            @Value("${telemetry.upgrade.metering-agent.desired-date}") String desiredMeteringAgentDate) {
        this.enabled = enabled;
        this.desiredCdpTelemetryVersion = desiredCdpTelemetryVersion;
        this.desiredCdpLoggingAgentVersion = desiredCdpLoggingAgentVersion;
        this.desiredMeteringAgentDate = desiredMeteringAgentDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDesiredCdpTelemetryVersion() {
        return desiredCdpTelemetryVersion;
    }

    public String getDesiredCdpLoggingAgentVersion() {
        return desiredCdpLoggingAgentVersion;
    }

    public String getDesiredMeteringAgentDate() {
        return desiredMeteringAgentDate;
    }
}

package com.sequenceiq.it.cloudbreak.action.freeipa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.FreeIpaClient;
import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.freeipa.FreeIpaTestDto;
import com.sequenceiq.it.cloudbreak.log.Log;

public class FreeIpaRefreshAction implements Action<FreeIpaTestDto, FreeIpaClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeIpaRefreshAction.class);

    @Override
    public FreeIpaTestDto action(TestContext testContext, FreeIpaTestDto testDto, FreeIpaClient client) throws Exception {
        testDto.setResponse(
                client.getFreeIpaClient().getFreeIpaV1Endpoint().describe(testDto.getRequest().getEnvironmentCrn())
        );
        Log.whenJson(LOGGER, " FreeIPA get response: ", testDto.getResponse());
        return testDto;
    }
}

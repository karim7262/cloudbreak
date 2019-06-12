package com.sequenceiq.freeipa.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.service.secret.model.SecretResponse;
import com.sequenceiq.cloudbreak.service.secret.service.SecretService;
import com.sequenceiq.environment.api.v1.credential.endpoint.CredentialEndpoint;
import com.sequenceiq.environment.api.v1.credential.model.response.CredentialResponse;
import com.sequenceiq.freeipa.dto.Credential;

@Service
public class CredentialService {

    @Inject
    private CredentialEndpoint credentialEndpoint;

    @Inject
    private SecretService secretService;

    public Credential getCredentialByEnvCrn(String envCrn) {
        CredentialResponse credentialResponse = credentialEndpoint.getByEnvCrn(envCrn);
        SecretResponse secretResponse = credentialResponse.getAttributes();
        String attributes = secretService.getByResponse(secretResponse);
        return new Credential(credentialResponse.getCloudPlatform(), credentialResponse.getName(), attributes, credentialResponse.getCrn());
    }
}

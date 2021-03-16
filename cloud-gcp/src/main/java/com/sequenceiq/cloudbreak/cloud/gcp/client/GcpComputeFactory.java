package com.sequenceiq.cloudbreak.cloud.gcp.client;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.compute.Compute;
import com.sequenceiq.cloudbreak.cloud.event.credential.CredentialVerificationException;
import com.sequenceiq.cloudbreak.cloud.model.CloudCredential;

@Service
public class GcpComputeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcpComputeFactory.class);

    @Inject
    private JsonFactory jsonFactory;

    @Inject
    private GcpCredentialFactory gcpCredentialFactory;

    @Inject
    private ApacheHttpTransport gcpApacheHttpTransport;

    public Compute buildCompute(CloudCredential cloudCredential) {
        try {
            GoogleCredential credential = gcpCredentialFactory.buildCredential(cloudCredential, gcpApacheHttpTransport);
            return new Compute.Builder(
                    gcpApacheHttpTransport, jsonFactory, null).setApplicationName(cloudCredential.getName())
                    .setHttpRequestInitializer(credential)
                    .build();
        } catch (Exception e) {
            LOGGER.warn("Error occurred while building Google Compute access.", e);
            throw new CredentialVerificationException("Error occurred while building Google Compute access.", e);
        }
    }

}
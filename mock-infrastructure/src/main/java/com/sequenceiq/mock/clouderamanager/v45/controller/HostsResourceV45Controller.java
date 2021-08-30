package com.sequenceiq.mock.clouderamanager.v45.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.sequenceiq.mock.clouderamanager.base.HostsResourceOperation;
import com.sequenceiq.mock.swagger.model.ApiCommand;
import com.sequenceiq.mock.swagger.model.ApiHost;
import com.sequenceiq.mock.swagger.model.ApiHostList;
import com.sequenceiq.mock.swagger.model.ApiHostsToRemoveArgs;
import com.sequenceiq.mock.swagger.v45.api.HostsResourceApi;

@Controller
public class HostsResourceV45Controller implements HostsResourceApi {

    @Inject
    private HostsResourceOperation hostsResourceOperation;

    @Override
    public ResponseEntity<ApiHost> deleteHost(String mockUuid, String hostId) {
        return hostsResourceOperation.deleteHost(mockUuid, hostId);
    }

    @Override
    public ResponseEntity<ApiHost> readHost(String mockUuid, String hostId, @Valid String view) {
        return hostsResourceOperation.readHost(mockUuid, hostId, view);
    }

    @Override
    public ResponseEntity<ApiHostList> readHosts(String mockUuid, @Valid String configName, @Valid String configValue, @Valid String view) {
        return hostsResourceOperation.readHosts(mockUuid, configName, configValue, view);
    }

    @Override
    public ResponseEntity<ApiCommand> removeHostsFromCluster(String mockUuid, @Valid ApiHostsToRemoveArgs body) {
        return hostsResourceOperation.removeHostsFromCluster(mockUuid, body);
    }
}

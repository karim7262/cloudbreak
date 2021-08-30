package com.sequenceiq.mock.clouderamanager.v45.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.sequenceiq.mock.clouderamanager.base.AuthRolesResourceOperation;
import com.sequenceiq.mock.swagger.model.ApiAuthRoleMetadataList;
import com.sequenceiq.mock.swagger.v45.api.AuthRolesResourceApi;

@Controller
public class AuthRolesResourceV45Controller implements AuthRolesResourceApi {

    @Inject
    private AuthRolesResourceOperation authRolesResourceOperation;

    @Override
    public ResponseEntity<ApiAuthRoleMetadataList> readAuthRolesMetadata(String mockUuid, @Valid String view) {
        return authRolesResourceOperation.readAuthRolesMetadata(mockUuid, view);
    }
}

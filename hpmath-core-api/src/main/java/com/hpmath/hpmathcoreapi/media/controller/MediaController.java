package com.hpmath.hpmathcoreapi.media.controller;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.dir.dto.DeleteMediaCommand;
import com.hpmath.hpmathcoreapi.media.service.MediaDeleteService;
import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import com.hpmath.hpmathwebcommon.authenticationV2.Authorization;
import com.hpmath.hpmathwebcommon.authenticationV2.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaDeleteService mediaDeleteService;

    @DeleteMapping(value = "/api/media")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        mediaDeleteService.delete(new DeleteMediaCommand(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }
}

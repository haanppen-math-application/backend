package com.hpmath.app.media.api.controller;

import com.hpmath.common.Role;
import com.hpmath.domain.media.dto.DeleteMediaCommand;
import com.hpmath.domain.media.service.MediaDeleteService;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
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
        mediaDeleteService.delete(new DeleteMediaCommand(mediaSrc, memberPrincipal.memberId(), memberPrincipal.role()));
        return ResponseEntity.noContent().build();
    }
}

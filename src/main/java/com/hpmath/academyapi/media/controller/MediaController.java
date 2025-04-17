package com.hpmath.academyapi.media.controller;

import com.hpmath.academyapi.dir.dto.DeleteMediaCommand;
import com.hpmath.academyapi.media.service.MediaDeleteService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaDeleteService mediaDeleteService;

    @DeleteMapping(value = "/api/media")
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        mediaDeleteService.delete(new DeleteMediaCommand(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }
}

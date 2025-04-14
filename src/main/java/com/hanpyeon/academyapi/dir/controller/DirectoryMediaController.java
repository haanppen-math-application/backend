package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.controller.Requests.SaveMediaToDirectoryRequest;
import com.hanpyeon.academyapi.dir.dto.DeleteMediaCommand;
import com.hanpyeon.academyapi.dir.dto.SaveMediaToDirectoryCommand;
import com.hanpyeon.academyapi.dir.service.DirectoryMediaService;
import com.hanpyeon.academyapi.media.service.MediaService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {
    private final MediaService mediaService;
    private final DirectoryMediaService directoryMediaService;

    @PostMapping("/api/directory/media")
    public ResponseEntity<Void> postMediaToDirectory(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            @ModelAttribute SaveMediaToDirectoryRequest request
    ) {
        final SaveMediaToDirectoryCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        directoryMediaService.saveToDirectory(command);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/api/directory/media")
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        mediaService.deleteMedia(new DeleteMediaCommand(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }
}

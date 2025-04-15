package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.controller.Requests.SaveMediaToDirectoryRequest;
import com.hanpyeon.academyapi.dir.dto.SaveMediaToDirectoryCommand;
import com.hanpyeon.academyapi.dir.service.DirectoryMediaService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {
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
}

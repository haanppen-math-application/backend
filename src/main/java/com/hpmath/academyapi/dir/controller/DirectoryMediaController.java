package com.hpmath.academyapi.dir.controller;

import com.hpmath.academyapi.dir.controller.Requests.SaveMediaToDirectoryRequest;
import com.hpmath.academyapi.dir.dto.SaveMediaToDirectoryCommand;
import com.hpmath.academyapi.dir.service.DirectoryMediaService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
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

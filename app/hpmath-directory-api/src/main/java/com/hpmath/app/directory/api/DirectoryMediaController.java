package com.hpmath.app.directory.api;

import com.hpmath.app.directory.api.Requests.SaveMediaToDirectoryRequest;
import com.hpmath.domain.directory.dto.SaveMediaToDirectoryCommand;
import com.hpmath.domain.directory.service.DirectoryMediaService;
import com.hpmath.hpmathcore.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {
    private final DirectoryMediaService directoryMediaService;

    @PostMapping("/api/directory/media")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> postMediaToDirectory(
            @LoginInfo MemberPrincipal memberPrincipal,
            @ModelAttribute SaveMediaToDirectoryRequest request
    ) {
        final SaveMediaToDirectoryCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        directoryMediaService.saveToDirectory(command);

        return ResponseEntity.ok().build();
    }
}

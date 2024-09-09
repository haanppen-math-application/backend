package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteAttachmentCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteAttachmentUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class DeleteAttachmentController {

    private final DeleteAttachmentUseCase deleteAttachmentUseCase;

    @DeleteMapping("/api/courses/memos/media/attachment")
    public ResponseEntity<?> deleteAttachment(
            @RequestParam final Long targetAttachmentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteAttachmentCommand command = DeleteAttachmentCommand.of(targetAttachmentId, memberPrincipal.memberId());
        deleteAttachmentUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }
}

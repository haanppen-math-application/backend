package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class UpdateMemoMediaController {

    private final UpdateMemoMediaUseCase updateMemoMediaUseCase;

    @PutMapping("/api/course/memo/media")
    @Operation(summary = "메모에 관련된 영상을 업데이트 하는 api", description = "요청 시 순서에 맞게 미디어 파일을 보내야 합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateMemoMedia(
            @RequestBody @Valid UpdateMemoMediaController.UpdateMemoMediaRequest updateMemoMediaRequest
    ) {
        final UpdateMediaMemoCommand command = updateMemoMediaRequest.toCommand();
        updateMemoMediaUseCase.updateMediaMemo(command);
        return ResponseEntity.ok().build();
    }

    record UpdateMemoMediaRequest(
            @Nonnull Long memoId,
            @Nonnull List<String> mediaSource
    ) {
        UpdateMediaMemoCommand toCommand() {
            return new UpdateMediaMemoCommand(memoId, mediaSource);
        }
    }
}

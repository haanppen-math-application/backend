package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.controller.Requests.MediaSaveRequest;
import com.hanpyeon.academyapi.dir.controller.Requests.MediaSaveResponse;
import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.DeleteMediaCommand;
import com.hanpyeon.academyapi.dir.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.dir.service.MediaService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/api/directory/media", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "서버의 디렉토리 서비스에 파일을 저장하는 api 입니다.")
    public ResponseEntity<MediaSaveResponse> saveMedia(
            @RequestPart(value = "media") @Nonnull final MultipartFile multipartFile,
            @RequestPart(value = "info") @Valid final MediaSaveRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UploadMediaCommand mediaSaveDto = request.toCommand(multipartFile, memberPrincipal.memberId());
        final ChunkStoreResult chunkStoreResult = mediaService.uploadChunk(mediaSaveDto);

        return mapToResponse(chunkStoreResult);
    }

    @DeleteMapping(value = "/api/directory/media")
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        mediaService.deleteMedia(new DeleteMediaCommand(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<MediaSaveResponse> mapToResponse(final ChunkStoreResult chunkStoreResult) {
        final MediaSaveResponse mediaSaveResponse = MediaSaveResponse.of(chunkStoreResult);
        if (!chunkStoreResult.getIsCompleted() && !chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.accepted().body(mediaSaveResponse);
        }
        if (chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mediaSaveResponse);
        }
        return ResponseEntity.created(null).build();
    }

}

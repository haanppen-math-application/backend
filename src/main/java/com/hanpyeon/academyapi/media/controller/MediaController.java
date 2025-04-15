package com.hanpyeon.academyapi.media.controller;

import static com.hanpyeon.academyapi.media.controller.Requests.MediaSaveRequest;

import com.hanpyeon.academyapi.dir.dto.DeleteMediaCommand;
import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.media.service.MediaDeleteService;
import com.hanpyeon.academyapi.media.service.MediaUploadService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/media/chunks")
@RequiredArgsConstructor
public class MediaController {
    private final MediaUploadService mediaUploadService;
    private final MediaDeleteService mediaDeleteService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "청크 파일을 저장하는 api 입니다.")
    public ResponseEntity<Responses.MediaSaveResponse> saveMedia(
            @RequestPart(value = "media") @Nonnull final MultipartFile multipartFile,
            @RequestPart(value = "info") @Valid final MediaSaveRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UploadMediaCommand mediaSaveDto = request.toCommand(multipartFile, memberPrincipal.memberId());
        final ChunkStoreResult chunkStoreResult = mediaUploadService.upload(mediaSaveDto);

        return mapToResponse(chunkStoreResult);
    }


    @DeleteMapping(value = "/api/directory/media")
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        mediaDeleteService.delete(new DeleteMediaCommand(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Responses.MediaSaveResponse> mapToResponse(final ChunkStoreResult chunkStoreResult) {
        final Responses.MediaSaveResponse mediaSaveResponse = Responses.MediaSaveResponse.of(chunkStoreResult);
        if (!chunkStoreResult.getIsCompleted() && !chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.accepted().body(mediaSaveResponse);
        }
        if (chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mediaSaveResponse);
        }
        return ResponseEntity.created(null).build();
    }
}

package com.hpmath.hpmathcoreapi.media.controller;

import com.hpmath.hpmathcoreapi.media.controller.Requests.ChunkMergeRequestV2;
import com.hpmath.hpmathcoreapi.media.controller.Requests.ChunkUploadRequestV2;
import com.hpmath.hpmathcoreapi.media.controller.Requests.ChunkUploadStartRequestV2;
import com.hpmath.hpmathcoreapi.media.controller.Responses.ChunkUploadStartResponse;
import com.hpmath.hpmathcoreapi.media.controller.Responses.RequiredChunkPartsResponse;
import com.hpmath.hpmathcoreapi.media.dto.ChunkMergeCommandV2;
import com.hpmath.hpmathcoreapi.media.dto.ChunkUploadCommand;
import com.hpmath.hpmathcoreapi.media.dto.ChunkUploadInitializeCommand;
import com.hpmath.hpmathcoreapi.media.dto.RequiredChunkInfo;
import com.hpmath.hpmathcoreapi.media.dto.UploadInitializeResult;
import com.hpmath.hpmathcoreapi.media.service.uploadV2.MultiPartUploadService;
import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import com.hpmath.hpmathwebcommon.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/media/chunks")
public class ChunkUploadControllerV2 {
    private final MultiPartUploadService multiPartUploadService;

    @PostMapping("/init")
    public ResponseEntity<ChunkUploadStartResponse> initializeMultiPartsUpload(
            @RequestBody ChunkUploadStartRequestV2 request
    ) {
        final ChunkUploadInitializeCommand command = request.toCommand();
        final UploadInitializeResult result = multiPartUploadService.initialize(command);

        return ResponseEntity.ok(ChunkUploadStartResponse.of(result));
    }

    @GetMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "청크 파일을 저장하는 api 입니다.")
    public ResponseEntity<RequiredChunkPartsResponse> getRequiredParts(
            @RequestParam(required = true) final String uniqueId
    ) {
        final RequiredChunkInfo info = multiPartUploadService.getNeedsChunkNumbers(uniqueId);

        return ResponseEntity.ok(RequiredChunkPartsResponse.of(info));
    }

    @PostMapping
    public ResponseEntity<Void> uploadPart(
            @ModelAttribute final ChunkUploadRequestV2 chunkUploadRequest
    ) {
        final ChunkUploadCommand command = chunkUploadRequest.toCommand();
        multiPartUploadService.upload(command);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> mergeChunks(
            @RequestBody final ChunkMergeRequestV2 chunkMergeRequestV2,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final ChunkMergeCommandV2 command = chunkMergeRequestV2.toCommand(memberPrincipal.memberId());
        multiPartUploadService.mergeAll(command);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(
            @RequestParam(required = true) final String uniqueId
    ) {
        multiPartUploadService.remove(uniqueId);

        return ResponseEntity.ok().build();
    }
}

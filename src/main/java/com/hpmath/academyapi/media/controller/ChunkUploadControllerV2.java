package com.hpmath.academyapi.media.controller;

import com.hpmath.academyapi.media.controller.Requests.ChunkMergeRequestV2;
import com.hpmath.academyapi.media.controller.Requests.ChunkUploadRequestV2;
import com.hpmath.academyapi.media.controller.Requests.ChunkUploadStartRequestV2;
import com.hpmath.academyapi.media.controller.Responses.ChunkUploadStartResponse;
import com.hpmath.academyapi.media.controller.Responses.RequiredChunkPartsResponse;
import com.hpmath.academyapi.media.dto.ChunkMergeCommandV2;
import com.hpmath.academyapi.media.dto.ChunkUploadCommand;
import com.hpmath.academyapi.media.dto.ChunkUploadInitializeCommand;
import com.hpmath.academyapi.media.dto.RequiredChunkInfo;
import com.hpmath.academyapi.media.dto.UploadInitializeResult;
import com.hpmath.academyapi.media.service.uploadV2.MultiPartUploadService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Operation(summary = "파일을 보내기 전, 초기화 API", description = "현재 파일을 몇개의 파트( Part )로 잘라서 보낼지 결정하면 됩니다. ")
    public ResponseEntity<ChunkUploadStartResponse> initializeMultiPartsUpload(
            @RequestBody ChunkUploadStartRequestV2 request
    ) {
        final ChunkUploadInitializeCommand command = request.toCommand();
        final UploadInitializeResult result = multiPartUploadService.initialize(command);

        return ResponseEntity.ok(ChunkUploadStartResponse.of(result));
    }

    @GetMapping
    @Operation(summary = "보내야 하는 목록 조회", description = "현재 파트 중, 몇번째가 안보내졌는지 조회")
    public ResponseEntity<RequiredChunkPartsResponse> getRequiredParts(
            @RequestParam(required = true) final String uniqueId
    ) {
        final RequiredChunkInfo info = multiPartUploadService.getNeedsChunkNumbers(uniqueId);

        return ResponseEntity.ok(RequiredChunkPartsResponse.of(info));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "청크 파일을 저장하는 API", description = "초기화 시 받은 uniqueId, 현재 파일이 몇번째인지를 보내면 됩니다.")
    public ResponseEntity<Void> uploadPart(
            @ModelAttribute final ChunkUploadRequestV2 chunkUploadRequest
    ) {
        final ChunkUploadCommand command = chunkUploadRequest.toCommand();
        multiPartUploadService.upload(command);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "청크 파일을 모두 합치기", description = "안보내진 부분이 있다면, error ( Test 용이라 로그인 필요 없는 상태 )")
    public ResponseEntity<Void> mergeChunks(
            @RequestBody final ChunkMergeRequestV2 chunkMergeRequestV2,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final ChunkMergeCommandV2 command = chunkMergeRequestV2.toCommand(1L);
        multiPartUploadService.mergeAll(command);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "해당 파일에 대한 정보 모두 삭제", description = "파일 전송을 중단하고 싶을떄, 삭제 하는 API")
    public ResponseEntity<Void> remove(
            @RequestParam(required = true) final String uniqueId
    ) {
        multiPartUploadService.remove(uniqueId);

        return ResponseEntity.ok().build();
    }
}

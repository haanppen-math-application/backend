package com.hpmath.app.api.media.controller;

import com.hpmath.common.Role;
import com.hpmath.app.api.media.controller.Requests.ChunkMergeRequestV2;
import com.hpmath.app.api.media.controller.Requests.ChunkUploadRequestV2;
import com.hpmath.app.api.media.controller.Requests.ChunkUploadStartRequestV2;
import com.hpmath.app.api.media.controller.Responses.ChunkUploadStartResponse;
import com.hpmath.app.api.media.controller.Responses.RequiredChunkPartsResponse;
import com.hpmath.domain.media.dto.ChunkMergeCommandV2;
import com.hpmath.domain.media.dto.ChunkUploadCommand;
import com.hpmath.domain.media.dto.ChunkUploadInitializeCommand;
import com.hpmath.domain.media.dto.RequiredChunkInfo;
import com.hpmath.domain.media.dto.StoredFileResult;
import com.hpmath.domain.media.dto.UploadInitializeResult;
import com.hpmath.domain.media.service.uploadV2.MultiPartUploadService;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
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
    @Operation(summary = "파일을 보내기 전, 초기화 API", description = "현재 파일을 몇개의 파트( Part )로 잘라서 보낼지 결정하면 됩니다. ")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<ChunkUploadStartResponse> initializeMultiPartsUpload(
            @RequestBody ChunkUploadStartRequestV2 request
    ) {
        final ChunkUploadInitializeCommand command = request.toCommand();
        final UploadInitializeResult result = multiPartUploadService.initialize(command);

        return ResponseEntity.ok(ChunkUploadStartResponse.of(result));
    }

    @GetMapping
    @Operation(summary = "보내야 하는 목록 조회", description = "현재 파트 중, 몇번째가 안보내졌는지 조회")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<RequiredChunkPartsResponse> getRequiredParts(
            @RequestParam(required = true) final String uniqueId
    ) {
        final RequiredChunkInfo info = multiPartUploadService.getNeedsChunkNumbers(uniqueId);

        return ResponseEntity.ok(RequiredChunkPartsResponse.of(info));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "청크 파일을 저장하는 API", description = "초기화 시 받은 uniqueId, 현재 파일이 몇번째인지를 보내면 됩니다.")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> uploadPart(
            @ModelAttribute final ChunkUploadRequestV2 chunkUploadRequest
    ) {
        final ChunkUploadCommand command = chunkUploadRequest.toCommand();
        multiPartUploadService.upload(command);

        return ResponseEntity.accepted().build();
    }

    @PutMapping
    @Operation(summary = "청크 파일을 모두 합치기", description = "안보내진 부분이 있다면, error ( Test 용이라 로그인 필요 없는 상태 )")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<StoredFileResult> mergeChunks(
            @RequestBody final ChunkMergeRequestV2 chunkMergeRequestV2,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final ChunkMergeCommandV2 command = chunkMergeRequestV2.toCommand(1L);
        final StoredFileResult result = multiPartUploadService.mergeAll(command);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    @Operation(summary = "해당 파일에 대한 정보 모두 삭제", description = "파일 전송을 중단하고 싶을떄, 삭제 하는 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> remove(
            @RequestParam(required = true) final String uniqueId
    ) {
        multiPartUploadService.remove(uniqueId);

        return ResponseEntity.noContent().build();
    }
}
package com.hpmath.domain.media.service.uploadV2;

import com.hpmath.domain.media.dto.ChunkMergeCommandV2;
import com.hpmath.domain.media.dto.ChunkUploadCommand;
import com.hpmath.domain.media.dto.ChunkUploadInitializeCommand;
import com.hpmath.domain.media.dto.RequiredChunkInfo;
import com.hpmath.domain.media.dto.StoredFileResult;
import com.hpmath.domain.media.dto.UploadInitializeResult;
import com.hpmath.domain.media.entity.Media;
import com.hpmath.domain.media.repository.MediaRepository;
import com.hpmath.domain.media.storage.MediaStorage;
import com.hpmath.domain.media.storage.uploadfile.UploadFile;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultiPartUploadService {
    private final ChunkPartNumberManager chunkNumberManager;
    private final UploadIdLifeCycleManager uniqueIdManger;
    private final ChunkStorageV2 chunkStorage;
    private final MediaStorage mediaStorage;
    private final UniqueIdGenerator uniqueIdGenerator;

    private final MediaRepository mediaRepository;

    public UploadInitializeResult initialize(final ChunkUploadInitializeCommand command) {
        // 1. uniqueID 생성 후, 해당 ID를 가지고 있어야 함.
        final String uniqueId = uniqueIdGenerator.generateUniqueId();
        uniqueIdManger.addUniqueId(uniqueId);
        chunkNumberManager.initNewId(uniqueId, command.totalPartCount());
        return new UploadInitializeResult(uniqueId);
    }

    public void upload(final ChunkUploadCommand command) {
        // 1, uniqueID 를 바탕으로 PartNumber 데이터 업데이트
        // 2. 파일 저장 ( uniqueID, PartNumber ) 를 포함
        uniqueIdManger.updateLastAccessTime(command.uniqueId());
        chunkNumberManager.receiveChunkNumber(command.uniqueId(), command.partNumber());
        final ChunkUploadFileV2 file = new ChunkUploadFileV2(command.uniqueId(), command.multipartFile(), command.partNumber());

        chunkStorage.save(file);
    }

    public void remove(final String uniqueId) {
        // 1. unqiueID를 바탕으로 데이터 삭제 ( 파일 시스템 내부 )
        // 2. uniqueID 삭제

        uniqueIdManger.remove(uniqueId);
        chunkNumberManager.remove(uniqueId);
        chunkStorage.removeRelated(uniqueId);
    }

    public StoredFileResult mergeAll(final ChunkMergeCommandV2 mergeCommand) {
        // 1. uniqueID 로 파일 모두 조회
        // 2. partNumber가 모두 있는지 ( 순서대로 있는지 )
        // 3. Merge Start
        // 4. uniqueID 삭
        uniqueIdManger.updateLastAccessTime(mergeCommand.uniqueId());

        final List<Integer> targets = chunkNumberManager.getRequiredParts(mergeCommand.uniqueId());

        if (targets.isEmpty()) {
            final InputStream combinedInputStreams = chunkStorage.loadSequentialChunks(mergeCommand.uniqueId());
            final UploadFile mergedUploadFile = new CombinedFile(combinedInputStreams, mergeCommand.uniqueId(), mergeCommand.extension());
            mediaStorage.store(mergedUploadFile);
            remove(mergeCommand.uniqueId());

            mediaRepository.save(new Media(mergeCommand.userDefinedFileName() + mergeCommand.extension(), mergedUploadFile.getUniqueFileName(),
                    mergeCommand.requestMemberId(), mergeCommand.duration(), mergeCommand.fileSize()));
            return new StoredFileResult(mergedUploadFile.getUniqueFileName());
        }
        throw new IllegalArgumentException(targets.size() + " 개의 파일이 도착하지 않았습니다.");
    }

    public RequiredChunkInfo getNeedsChunkNumbers(
            final String uniqueId
    ) {
        uniqueIdManger.updateLastAccessTime(uniqueId);
        return new RequiredChunkInfo(chunkNumberManager.getRequiredParts(uniqueId));
    }
}

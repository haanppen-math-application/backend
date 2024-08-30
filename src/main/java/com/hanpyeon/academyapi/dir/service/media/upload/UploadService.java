package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {

    private final ChunkCreator chunkCreator;
    private final @Qualifier(value = "chunkStorage") ChunkStorage chunkStorage;
    private final MergedFileTransferManager mergedFileTransferManager;
    private final DirectoryMediaUpdateManager directoryMediaUpdateManager;

    public String upload(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkCreator.create(uploadMediaDto);
        chunkStorage.save(chunkedFile);
        if (chunkedFile.isLast()) {
            log.info("파일 전송 시작");
            final String savedFileName = mergedFileTransferManager.sendToMediaStorage(chunkStorage, chunkedFile.getChunkGroupInfo());
            directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), savedFileName);
            return savedFileName;
        }
        return null;
    }
}

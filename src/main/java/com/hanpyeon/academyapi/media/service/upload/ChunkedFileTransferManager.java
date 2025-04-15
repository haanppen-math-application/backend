package com.hanpyeon.academyapi.media.service.upload;

import com.hanpyeon.academyapi.media.storage.uploadfile.MergedUploadFile;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChunkedFileTransferManager {
    private final MediaStorage mediaStorage;

    public String sendToMediaStorage(final MergedUploadFile mergedUploadFile) {
        log.debug("합쳐진 파일 전송 시작");
        mediaStorage.store(mergedUploadFile);

        mergedUploadFile.completed();
        return mergedUploadFile.getUniqueFileName();
    }
}

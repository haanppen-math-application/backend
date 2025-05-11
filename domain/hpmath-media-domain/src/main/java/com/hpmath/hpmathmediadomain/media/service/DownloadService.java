package com.hpmath.hpmathmediadomain.media.service;

import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathmediadomain.media.dto.DownloadCommand;
import com.hpmath.hpmathmediadomain.media.dto.DownloadResult;
import com.hpmath.hpmathmediadomain.media.dto.MediaDto;
import com.hpmath.hpmathmediadomain.media.exception.MediaException;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import com.hpmath.hpmathmediadomain.media.storage.MediaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadService {

    private final MediaStorage mediaStorage;
    private final MediaRepository mediaRepository;

    public DownloadResult getResource(final DownloadCommand downloadCommand) {
        final MediaDto mediaDto = mediaStorage.loadFile(downloadCommand.getFileName());
        final String fileName = mediaRepository.findFileNameBySrc(downloadCommand.getFileName())
                .orElseThrow(() -> new MediaException("해당 파일의 이름 찾기 불가능", ErrorCode.NO_SUCH_MEDIA));
        log.info("FILENAME = " + fileName);
        return new DownloadResult(mediaDto.data(), fileName);
    }
}

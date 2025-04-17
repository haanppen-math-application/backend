package com.hpmath.hpmathcoreapi.media.service;

import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.media.dto.DownloadCommand;
import com.hpmath.hpmathcoreapi.media.dto.DownloadResult;
import com.hpmath.hpmathcoreapi.media.dto.MediaDto;
import com.hpmath.hpmathcoreapi.media.exception.MediaException;
import com.hpmath.hpmathcoreapi.media.repository.MediaRepository;
import com.hpmath.hpmathcoreapi.media.storage.MediaStorage;
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

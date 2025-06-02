package com.hpmath.domain.media.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.media.dto.DownloadCommand;
import com.hpmath.domain.media.dto.DownloadResult;
import com.hpmath.domain.media.dto.MediaDto;
import com.hpmath.domain.media.exception.MediaException;
import com.hpmath.domain.media.repository.MediaRepository;
import com.hpmath.domain.media.storage.MediaStorage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DownloadService {
    private final MediaStorage mediaStorage;
    private final MediaRepository mediaRepository;

    public DownloadResult getResource(@Valid final DownloadCommand downloadCommand) {
        final MediaDto mediaDto = mediaStorage.loadFile(downloadCommand.getFileName());
        final String fileName = mediaRepository.findFileNameBySrc(downloadCommand.getFileName())
                .orElseThrow(() -> new MediaException("해당 파일의 이름 찾기 불가능", ErrorCode.NO_SUCH_MEDIA));
        log.info("FILENAME = " + fileName);
        return new DownloadResult(mediaDto.data(), fileName);
    }
}

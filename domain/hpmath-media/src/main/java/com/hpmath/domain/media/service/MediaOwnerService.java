package com.hpmath.domain.media.service;

import com.hpmath.domain.media.repository.MediaRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class MediaOwnerService {
    private final MediaRepository mediaRepository;

    @Transactional
    public void updateMemberInfos(@NotNull final List<Long> memberIds){
        final Integer count = mediaRepository.updateMemberIdsToNull(memberIds);
        log.debug("update memberIds to null: {}, where ids: {}", count, memberIds);
    }
}

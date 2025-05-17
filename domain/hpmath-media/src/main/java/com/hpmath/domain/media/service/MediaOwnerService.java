package com.hpmath.domain.media.service;

import com.hpmath.domain.media.repository.MediaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaOwnerService {
    private final MediaRepository mediaRepository;

    @Transactional
    public void updateMemberInfos(final List<Long> memberIds){
        final Integer count = mediaRepository.updateMemberIdsToNull(memberIds);
        log.debug("update memberIds to null: {}, where ids: {}", count, memberIds);
    }
}

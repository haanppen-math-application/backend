package com.hanpyeon.academyapi.media.service.upload;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.media.service.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaUpdateManager {

    private final MemberRepository memberRepository;
    private final MediaRepository mediaRepository;

    @Transactional
    public void update(final ChunkGroupInfo chunkGroupInfo, final Long duration, final String savedPath, final Long memberId, final Long fileSize) {
        final Media media = create(chunkGroupInfo.getFileName() + chunkGroupInfo.getExtension(), savedPath, memberId, duration, fileSize);
        mediaRepository.save(media);
    }

    private Media create(final String fileName, final String savedPath, final Long memberId, final Long duration, final Long fileSize) {
        final Member member = loadMember(memberId);
        return new Media(fileName, savedPath, member, duration, fileSize);
    }

    private Member loadMember(final Long memberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
    }
}

package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadMemoMediaPort;
import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadMemoMediaAdapter implements LoadMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    private final MediaClient mediaClient;

    @Override
    public List<MemoMedia> loadMedia(Long memoId) {
        return memoMediaRepository.findAllByMemo_Id(memoId)
                .stream().map(memoMedia ->  {
                    final MediaInfo mediaInfo = mediaClient.getFileInfo(memoMedia.getMediaSrc());
                    return MemoMedia.createByEntity(memoMedia.getId(), mediaInfo.mediaName(), mediaInfo.mediaSrc(), null, memoMedia.getSequence());
                })
                .collect(Collectors.toList());
    }
}

package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.port.out.LoadMemoMediaPort;
import com.hpmath.academyapi.course.domain.MemoMedia;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadMemoMediaAdapter implements LoadMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    @Override
    public List<MemoMedia> loadMedia(Long memoId) {
        return memoMediaRepository.findAllByMemo_Id(memoId)
                .stream().map(memoMedia ->  {
                    final com.hpmath.academyapi.media.entity.Media media = memoMedia.getMedia();
                    return MemoMedia.createByEntity(memoMedia.getId(), media.getMediaName(), media.getSrc(), null, memoMedia.getSequence());
                })
                .collect(Collectors.toList());
    }
}

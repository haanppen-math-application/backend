package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadMediasPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoPort;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.Media;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.Memo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateMemoMediaService implements UpdateMemoMediaUseCase {
    private final LoadMemoPort loadMemoPort;
    private final LoadMediasPort loadMediasPort;
    private final UpdateMemoMediaPort updateMemoMediaPort;

    @Override
    @Transactional
    public void updateMediaMemo(UpdateMediaMemoCommand updateMediaMemoCommand) {
        final Memo targetMemo = loadMemoPort.loadMemo(updateMediaMemoCommand.memoId()) ;
        final List<Media> selectedMediasWithSequence = loadMediasPort.loadMedias(updateMediaMemoCommand.mediaId());
        final List<MemoMedia> sequentialMemoMedias = mapToMemoMedia(selectedMediasWithSequence);
        targetMemo.setMedias(sequentialMemoMedias);
        updateMemoMediaPort.update(targetMemo);
    }

    private List<MemoMedia> mapToMemoMedia(final List<Media> medias) {
        final List<MemoMedia> resultMemoMedia = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            final MemoMedia memoMedia = medias.get(i).mapToMemoMedia(i);
            resultMemoMedia.add(memoMedia);
        }
        return resultMemoMedia;
    }
}

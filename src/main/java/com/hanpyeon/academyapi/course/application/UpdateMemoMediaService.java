package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadMediasPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoPort;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.Media;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateMemoMediaService implements UpdateMemoMediaUseCase {
    private final LoadMemoPort loadMemoPort;
    private final LoadMediasPort loadMediasPort;
    private final UpdateMemoMediaPort updateMemoMediaPort;

    @Override
    @Transactional
    public void updateMediaMemo(UpdateMediaMemoCommand updateMediaMemoCommand) {
        validateDuplicated(updateMediaMemoCommand.mediaSequences());

        final Memo targetMemo = loadMemoPort.loadMemo(updateMediaMemoCommand.memoId());
        final List<Media> selectedMediasWithoutSequence = loadMediasPort.loadMedias(updateMediaMemoCommand.mediaSequences());
        final List<MemoMedia> sequentialMemoMedias = mapToMemoMedia(selectedMediasWithoutSequence, updateMediaMemoCommand.mediaSequences());
        targetMemo.setMedias(sequentialMemoMedias);

        updateMemoMediaPort.update(targetMemo);
    }

    private void validateDuplicated(final List<String> mediaSequences) {
        if (Integer.toUnsignedLong(mediaSequences.size()) != mediaSequences.stream().distinct().count()) {
            throw new CourseException("동일한 메모에 중복된 영상 저장 불가", ErrorCode.MEMO_MEDIA_DUPLICATED);
        }
    }



    private List<MemoMedia> mapToMemoMedia(final List<Media> medias, final List<String> mediaSequences) {
        final List<MemoMedia> resultMemoMedia = new ArrayList<>();
        for (int i = 0; i < mediaSequences.size(); i++) {
            for (final Media targetMedia : medias) {
                if (targetMedia.getMediaSource().equals(mediaSequences.get(i))) {
                    log.debug(mediaSequences.get(i) + " " + i);
                    resultMemoMedia.add(new MemoMedia(targetMedia.getMediaName(), targetMedia.getMediaSource(), null, i));
                    break;
                }
            }
        }
        return resultMemoMedia;
    }
}

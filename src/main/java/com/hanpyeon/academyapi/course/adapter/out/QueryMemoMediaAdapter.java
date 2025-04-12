package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.controller.Responses.MediaViewResponse;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoMediaPort;
import com.hanpyeon.academyapi.course.entity.MemoMedia;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryMemoMediaAdapter implements QueryMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    private final QueryMemoMediaAttachmentAdapter queryMemoMediaAttachmentAdapter;

    @Override
    public List<MediaViewResponse> queryMedias(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new CourseException("메모를 찾을 수 없음 : " + memoId, ErrorCode.MEMO_NOT_EXIST);
        }
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        return memoMedias.stream()
                .map(memoMedia -> {
                    final Media media = memoMedia.getMedia();
                    final MediaViewResponse view = new MediaViewResponse(memoMedia.getId(), media.getMediaName(), media.getSrc(), memoMedia.getSequence(), queryMemoMediaAttachmentAdapter.query(memoMedia.getId()));
                    log.debug(view.toString());
                    return view;
                })
                .collect(Collectors.toList());
    }
}

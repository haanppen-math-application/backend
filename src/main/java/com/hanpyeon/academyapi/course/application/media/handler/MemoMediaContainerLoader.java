package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoMediaContainerLoader {
    private final LoadMemoMediaPort loadMemoMediaPort;

    public MemoMediaContainer load(final UpdateMediaMemoCommand command) {
        final List<MemoMedia> memoMedias = loadMemoMediaPort.loadMedia(command.memoId());
        validate(command.mediaSequences().size(), memoMedias.size());
        return MemoMediaContainer.of(memoMedias, command.memoId());
    }

    private void validate(final int commandCount, final int loadedMemoMediaCount) {
        if (commandCount != loadedMemoMediaCount) {
            throw new MemoMediaException("입력 순서 요청 갯수 : " + commandCount + " 저장된 수업미디어 갯수 : " + loadedMemoMediaCount, ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION);
        }
    }
}

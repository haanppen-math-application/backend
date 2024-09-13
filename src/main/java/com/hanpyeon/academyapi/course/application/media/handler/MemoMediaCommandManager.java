package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaRegisterCommand;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoMediaCommandManager {

    private final List<MemoMediaCommandHandler> memoMediaUpdateHandlers;

    public MemoMedia handle(final MemoMediaRegisterCommand mediaDto) {
        return memoMediaUpdateHandlers.stream()
                .filter(updateHandler -> updateHandler.applicable(mediaDto))
                .findAny()
                .orElseThrow(() -> new MemoMediaException("적용할 수 있는 핸들러가 없음 " + mediaDto, ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION))
                .handle(mediaDto);
    }
}

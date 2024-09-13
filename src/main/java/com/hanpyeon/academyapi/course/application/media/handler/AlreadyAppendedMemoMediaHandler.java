package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaRegisterCommand;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaByMemoMediaIdPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AlreadyAppendedMemoMediaHandler implements MemoMediaCommandHandler {

    private final LoadMemoMediaByMemoMediaIdPort loadMemoMediaByMemoMediaIdPort;

    @Override
    public MemoMedia handle(MemoMediaRegisterCommand memoMediaRegisterCommand) {
        final MemoMedia memoMedia = loadMemoMediaByMemoMediaIdPort.loadByMemoMediaId(memoMediaRegisterCommand.memoMediaId(), memoMediaRegisterCommand.memoId());
        return memoMedia;
    }

    @Override
    public boolean applicable(MemoMediaRegisterCommand mediaDto) {
        if (mediaDto.isNew()) {
            return false;
        }
        return true;
    }
}

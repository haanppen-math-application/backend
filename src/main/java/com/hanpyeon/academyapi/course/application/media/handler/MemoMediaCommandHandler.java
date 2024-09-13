package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaRegisterCommand;
import com.hanpyeon.academyapi.course.domain.MemoMedia;

interface MemoMediaCommandHandler {
    MemoMedia handle(final MemoMediaRegisterCommand memoMediaDto);

    boolean applicable(final MemoMediaRegisterCommand mediaDto);
}

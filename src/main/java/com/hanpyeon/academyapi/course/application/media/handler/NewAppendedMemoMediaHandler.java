package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaRegisterCommand;
import com.hanpyeon.academyapi.course.application.port.out.LoadMediaBySourcePort;
import com.hanpyeon.academyapi.course.domain.Media;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class NewAppendedMemoMediaHandler implements MemoMediaCommandHandler {

    private final LoadMediaBySourcePort loadMediaBySourcePort;

    @Override
    public MemoMedia handle(MemoMediaRegisterCommand memoMediaDto) {
        final Media media = loadMediaBySourcePort.loadMediaBySource(memoMediaDto.mediaSource());
        return MemoMedia.createByMedia(media, memoMediaDto.sequence());
    }

    @Override
    public boolean applicable(MemoMediaRegisterCommand mediaDto) {
        if (mediaDto.isNew()) {
            return true;
        }
        return false;
    }
}

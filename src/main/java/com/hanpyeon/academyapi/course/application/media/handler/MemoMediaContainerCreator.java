package com.hanpyeon.academyapi.course.application.media.handler;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaRegisterCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoMediaContainerCreator {
    private final MemoMediaCommandManager memoMediaCommandManager;

    public MemoMediaContainer createContainer(final UpdateMediaMemoCommand command) {
        final List<MemoMedia> memoMedias = loadMemoMedia(command.mediaSequences());
        return MemoMediaContainer.of(memoMedias, command.memoId());
    }

    private List<MemoMedia> loadMemoMedia(final List<MemoMediaRegisterCommand> commands) {
        return commands.stream()
                .map(memoMediaRegisterCommand -> memoMediaCommandManager.handle(memoMediaRegisterCommand))
                .collect(Collectors.toList());
    }
}

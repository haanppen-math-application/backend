package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingResult;
import com.hanpyeon.academyapi.media.storage.StreamingAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StreamingService {
    private final StreamingAccessor accessor;

    public StreamingResult stream(StreamingCommand streamingCommand) {
        final StreamingResult result = accessor.getChunk(streamingCommand);
        return result;
    }
}

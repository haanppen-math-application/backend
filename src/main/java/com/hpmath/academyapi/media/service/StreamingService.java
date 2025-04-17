package com.hpmath.academyapi.media.service;

import com.hpmath.academyapi.media.dto.StreamingCommand;
import com.hpmath.academyapi.media.dto.StreamingResult;
import com.hpmath.academyapi.media.storage.StreamingAccessor;
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

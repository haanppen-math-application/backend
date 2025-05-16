package com.hpmath.domain.media.service;

import com.hpmath.domain.media.dto.StreamingCommand;
import com.hpmath.domain.media.dto.StreamingResult;
import com.hpmath.domain.media.storage.StreamingAccessor;
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

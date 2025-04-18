package com.hpmath.hpmathmediadomain.media.service;

import com.hpmath.hpmathmediadomain.media.dto.StreamingCommand;
import com.hpmath.hpmathmediadomain.media.dto.StreamingResult;
import com.hpmath.hpmathmediadomain.media.storage.StreamingAccessor;
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

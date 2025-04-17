package com.hpmath.hpmathcoreapi.media.service;

import com.hpmath.hpmathcoreapi.media.dto.StreamingCommand;
import com.hpmath.hpmathcoreapi.media.dto.StreamingResult;
import com.hpmath.hpmathcoreapi.media.storage.StreamingAccessor;
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

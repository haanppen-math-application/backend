package com.hpmath.domain.media.storage;

import com.hpmath.domain.media.dto.ChunkedMediaDto;
import com.hpmath.domain.media.dto.MediaDto;
import com.hpmath.domain.media.dto.StreamingCommand;
import com.hpmath.domain.media.dto.StreamingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class StreamingAccessorImpl implements StreamingAccessor {

    private final MediaStorage mediaStorage;
    private final StreamProcessor streamProcessor;

    @Override
    public StreamingResult getChunk(StreamingCommand streamingCommand) {
        final MediaDto mediaDto = mediaStorage.loadFile(streamingCommand.getFileName());
        final ChunkedMediaDto chunkedMediaDto = streamProcessor.getProcessedInputStream(streamingCommand, mediaDto);
        return new StreamingResult(
                chunkedMediaDto.getInputStream(),
                mediaDto.mediaType(),
                chunkedMediaDto.getCurrentSize(),
                mediaDto.fileSize(),
                chunkedMediaDto.getStartIndex(),
                chunkedMediaDto.getEndIndex()
        );
    }
}

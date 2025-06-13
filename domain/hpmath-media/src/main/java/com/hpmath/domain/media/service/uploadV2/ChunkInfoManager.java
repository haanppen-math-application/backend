package com.hpmath.domain.media.service.uploadV2;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChunkInfoManager {
    private final ChunkTotalCountRepository chunkTotalCountRepository;
    private final ChunkPartNumberRepository chunkPartNumberRepository;

    private static final Duration TTL = Duration.ofMinutes(5);

    public void init(String uniqueId, Integer totalChunkCount) {
        chunkTotalCountRepository.init(uniqueId, totalChunkCount, TTL);
    }

    public void receiveChunk(String uniqueId, Integer partNumber) {
        chunkTotalCountRepository.updateTTLAndGetTotalCount(uniqueId, TTL);
        chunkPartNumberRepository.receive(uniqueId, partNumber, TTL);
    }

    public List<Integer> getRequiredPartNumbers(String uniqueId) {
        final List<Integer> receivedNumbers = chunkPartNumberRepository.getAllOf(uniqueId);
        final int totalCount = chunkTotalCountRepository.updateTTLAndGetTotalCount(uniqueId, TTL);

        return IntStream.range(0, totalCount)
                .filter(i -> !receivedNumbers.contains(i))
                .boxed()
                .toList();
    }

    public void remove(final String uniqueId) {
        chunkTotalCountRepository.remove(uniqueId);
        chunkPartNumberRepository.remove(uniqueId);
    }

    public boolean isExist(final String uniqueId) {
        return chunkTotalCountRepository.exists(uniqueId);
    }

    public boolean notExist(final String uniqueId) {
        return !isExist(uniqueId);
    }
}

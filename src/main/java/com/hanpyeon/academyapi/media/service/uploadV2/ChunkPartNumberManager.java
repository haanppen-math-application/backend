package com.hanpyeon.academyapi.media.service.uploadV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class ChunkPartNumberManager {
    private final Map<String, Set<Integer>> receivedNumbers = new ConcurrentHashMap<>();
    private final Map<String, Integer> idsTotalPartCount = new ConcurrentHashMap<>();

    synchronized void initNewId(final String uniqueId, final int totalCount) {
        if (exist(uniqueId)) {
            log.warn("Duplicate id found for unique id: {}", uniqueId);
            throw new IllegalArgumentException("이미 존재하는 키");
        }
        idsTotalPartCount.put(uniqueId, totalCount);
        receivedNumbers.put(uniqueId, createEmptySet());
        log.debug("New id inserted for unique id: {}", uniqueId);
        log.debug("Total count for unique id: {}", totalCount);
    }

    /**
     * 이미 해당 {@code chunkNumber}를 받았다면, 기록을 덮어씁니다.
     * @param uniqueId    유니크 id
     * @param chunkNumber 현재 파트의 번호
     */
    void receiveChunkNumber(final String uniqueId, final int chunkNumber) {
        if (exist(uniqueId)) {
            final int maxPartCount = idsTotalPartCount.get(uniqueId);
            if (chunkNumber >= maxPartCount || chunkNumber < 0) {
                log.warn("Invalid chunk number found for unique id: [{}] Chunk number: [{}]", uniqueId, chunkNumber);
                throw new IllegalArgumentException("청크 범위 초과");
            }

            final Set<Integer> partCounts = receivedNumbers.get(uniqueId);
            partCounts.add(chunkNumber);
            log.debug("received chunk number : {}", chunkNumber);
            log.debug("New chunk number inserted for unique id: {}", uniqueId);
            return;
        }
        log.warn("not exist key: {}", uniqueId);
        throw new IllegalArgumentException("존재하지 않는 키");
    }

    /**
     * 이미 수신한 part를 포험할 수 있습니다.
     *
     * @param uniqueId
     * @return
     */
    List<Integer> getRequiredParts(final String uniqueId) {
        if (exist(uniqueId)) {
            final Set<Integer> parts = receivedNumbers.get(uniqueId);
            final int totalCount = idsTotalPartCount.get(uniqueId);

            return getRequired(parts, totalCount);
        }
        log.warn("not exist key: {}", uniqueId);
        throw new IllegalArgumentException("존재하지 않는 키");
    }

    synchronized void remove(final String uniqueId) {
        log.debug("remove start unique id : {}", uniqueId);
        receivedNumbers.remove(uniqueId);
        idsTotalPartCount.remove(uniqueId);
        log.debug("removed end unique id: {}", uniqueId);
    }

    private boolean exist(final String uniqueId) {
        return receivedNumbers.containsKey(uniqueId) && idsTotalPartCount.containsKey(uniqueId);
    }

    private Set<Integer> createEmptySet() {
        return Collections.synchronizedSet(new HashSet<>());
    }

    private List<Integer> getRequired(final Set<Integer> receviedPartNumbers, final int totalCount) {
        final List<Integer> requiredParts = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            if (receviedPartNumbers.contains(i)) {
                continue;
            }
            requiredParts.add(i);
        }
        return requiredParts;
    }
}

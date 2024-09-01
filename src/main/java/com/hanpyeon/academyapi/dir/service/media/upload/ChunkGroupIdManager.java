package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
final class ChunkGroupIdManager {

    private static Map<ChunkGroupInfo, Group> countPerChunkMap = new ConcurrentHashMap<>();

    public String getGroupID(final ChunkGroupInfo chunkGroupInfo) {
        final Group groupSequence = countPerChunkMap.getOrDefault(chunkGroupInfo, Group.getUnique());
        countPerChunkMap.put(chunkGroupInfo, groupSequence);
        return groupSequence.groupId.toString();
    }

    public Long getChunkId(final ChunkGroupInfo chunkGroupInfo) {
        final Group group = countPerChunkMap.get(chunkGroupInfo);
        if (Objects.isNull(group)) {
            throw new ChunkException("해당 청크의 소속을 알 수 없습니다.",ErrorCode.CHUNK_GROUP_EXCEPTION);
        }
        return group.getCurrentIndexAndIncrease();
    }

    public boolean removeGroupID(final ChunkGroupInfo chunkGroupInfo) {
        if (Objects.isNull(countPerChunkMap.remove(chunkGroupInfo))) {
            throw new ChunkException("이미 다운로드 된 청크 입니다.", ErrorCode.CHUNK_GROUP_EXCEPTION);
        }
        return true;
    }

    public Long getGroupeNextChunkIndex(final ChunkGroupInfo chunkGroupInfo) {
        final Group group = countPerChunkMap.get(chunkGroupInfo);
        return group.nextChunkIndex;
    }

    public Long updateGroupNextChunkIndex(final ChunkGroupInfo chunkGroupInfo, final Long lastChunkSize) {
        final Group group = countPerChunkMap.get(chunkGroupInfo);
        group.nextChunkIndex += lastChunkSize;
        return group.nextChunkIndex;
    }


    @RequiredArgsConstructor
    private static class Group {
        private final UUID groupId;
        private Long chunkId = 0L;
        private Long nextChunkIndex = 0L;

        Long getCurrentIndexAndIncrease() {
            return chunkId++;
        }

        static Group getUnique() {
            final UUID uuid = UUID.randomUUID();
            return new Group(uuid);
        }
    }
}

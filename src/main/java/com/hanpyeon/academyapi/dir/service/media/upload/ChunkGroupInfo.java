package com.hanpyeon.academyapi.dir.service.media.upload;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
class ChunkGroupInfo {
    private final ChunkGroupIdManager chunkGroupIdManager;
    private final Long requestMemberId;
    private final String dirPath;
    private final String fileName;
    private final Long totalChunkSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkGroupInfo that = (ChunkGroupInfo) o;
        return Objects.equals(requestMemberId, that.requestMemberId) &&
                Objects.equals(dirPath, that.dirPath) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(totalChunkSize, that.totalChunkSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMemberId, dirPath, fileName, totalChunkSize);
    }

    public String getGroupId() {
        return chunkGroupIdManager.getGroupID(this);
    }

    public String getChunkUniqueId() {
        final String groupId = getGroupId();
        final Long groupIndex = chunkGroupIdManager.getChunkId(this);
        return groupId + "_" + groupIndex;
    }

    public boolean clear() {
        return this.chunkGroupIdManager.removeGroupID(this);
    }

    public boolean isAllReceived(final Long currentReceivedSize) {
        return this.totalChunkSize.equals(currentReceivedSize);
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public String getFileName() {
        return this.fileName;
    }
}

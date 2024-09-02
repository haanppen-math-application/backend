package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class ChunkGroupInfoImpl implements ChunkGroupInfo {
    private final ChunkGroupIdManager chunkGroupIdManager;
    private final Long requestMemberId;
    private final String dirPath;
    private final String fileName;
    private final Long totalChunkSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkGroupInfoImpl that = (ChunkGroupInfoImpl) o;
        return Objects.equals(requestMemberId, that.requestMemberId) &&
                Objects.equals(dirPath, that.dirPath) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(totalChunkSize, that.totalChunkSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMemberId, dirPath, fileName, totalChunkSize);
    }

    @Override
    public void init() {
        this.getGroupId();
    }

    @Override
    public String getGroupId() {
        return chunkGroupIdManager.getGroupID(this);
    }

    @Override
    public String getChunkUniqueId() {
        final String groupId = getGroupId();
        final Long groupIndex = chunkGroupIdManager.getChunkId(this);
        return groupId + "_" + groupIndex;
    }

    @Override
    public boolean clear() {
        return this.chunkGroupIdManager.removeGroupID(this);
    }

    @Override
    public String getDirPath() {
        return this.dirPath;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public boolean isAllReceived(final Long receivedFileSize) {
        return this.totalChunkSize.equals(receivedFileSize);
    }

    @Override
    public void isMatchToCurrIndex(final Long lastChunkIndex) {
        log.debug("그룹에 전송된 청크 index : " + lastChunkIndex);
        final Long groupReceivedChunkIndex = chunkGroupIdManager.getGroupeNextChunkIndex(this);
        log.debug("그룹이 필요한 청크 index : " + groupReceivedChunkIndex);
        if (lastChunkIndex.equals(groupReceivedChunkIndex)) {
            return;
        }
        throw new ChunkException("청크 순서 오류. 현재 필요 시퀀스 : " + groupReceivedChunkIndex +
                "\n" + "수신 시퀀스 : " + lastChunkIndex ,ErrorCode.CHUNK_GROUP_EXCEPTION);
    }

    @Override
    public void updateGroupIndex(final Long lastChunkSize) {
        this.chunkGroupIdManager.updateGroupNextChunkIndex(this, lastChunkSize);
    }

    @Override
    public RequireNextChunk isCompleted() {
        final Long nextChunkIndex = chunkGroupIdManager.getGroupeNextChunkIndex(this);
        if (nextChunkIndex + 1L == totalChunkSize) {
            return RequireNextChunk.completed();
        }
        return RequireNextChunk.needMore(nextChunkIndex);
    }
}

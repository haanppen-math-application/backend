package com.hpmath.domain.directory.dto;

import com.hpmath.client.media.MediaClient.MediaInfo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VideoView extends FileView {
    private final Long runtimeDuration;
    private final Long fileSize;

    public VideoView(
            String fileName,
            Boolean isDir,
            String path,
            LocalDateTime createdTime,
            Boolean canViewByEveryone,
            Boolean canModifyByEveryone,
            Long runtimeDuration,
            Long fileSize
    ) {
        super(fileName, isDir, path, createdTime, canViewByEveryone, canModifyByEveryone);
        this.runtimeDuration = runtimeDuration;
        this.fileSize = fileSize;
    }

    public static VideoView from(final MediaInfo mediaInfo) {
        return new VideoView(
                mediaInfo.mediaName(),
                false,
                mediaInfo.mediaSrc(),
                mediaInfo.createdTime(),
                true,
                false,
                mediaInfo.runtimeDuration(),
                mediaInfo.fileSize());
    }
}

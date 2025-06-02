package com.hpmath.domain.directory.service.query;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dto.FileView;
import com.hpmath.domain.directory.dto.VideoView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BasicFileViewMapper implements FileViewMapper {
    private final MediaClient mediaClient;

    @Override
    public FileView create(Directory directory) {
        final String parsedDirName = parseDirName(directory.getPath());
        return new FileView(parsedDirName, true, directory.getPath(), directory.getCreatedTime(), directory.getCanViewByEveryone(), directory.getCanAddByEveryone());
    }

    @Override
    public FileView create(String mediaSrc) {
        final MediaInfo mediaInfo = mediaClient.getFileInfo(mediaSrc);
        return VideoView.from(mediaInfo);
    }

    private String parseDirName(final String dirAbsolutePath) {
        String[] dirPath = dirAbsolutePath.split("/");
        return dirPath[dirPath.length-1];
    }
}

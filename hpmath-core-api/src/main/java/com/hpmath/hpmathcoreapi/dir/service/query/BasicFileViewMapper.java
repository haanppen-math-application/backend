package com.hpmath.hpmathcoreapi.dir.service.query;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dto.FileView;
import com.hpmath.hpmathcoreapi.dir.dto.VideoView;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicFileViewMapper implements FileViewMapper {

    @Override
    public FileView create(Directory directory) {
        final String parsedDirName = parseDirName(directory.getPath());
        return new FileView(parsedDirName, true, directory.getPath(), directory.getCreatedTime(), directory.getCanViewByEveryone(), directory.getCanAddByEveryone());
    }

    @Override
    public FileView create(Media media) {
        return new VideoView(media.getMediaName(), false, media.getSrc(), media.getCreatedTime(), true, false,
                media.getDuration(), media.getSize());
    }

    private String parseDirName(final String dirAbsolutePath) {
        String[] dirPath = dirAbsolutePath.split("/");
        return dirPath[dirPath.length-1];
    }
}

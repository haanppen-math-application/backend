package com.hpmath.academyapi.dir.service.query;

import com.hpmath.academyapi.dir.dao.Directory;
import com.hpmath.academyapi.dir.dto.FileView;
import com.hpmath.academyapi.media.entity.Media;

interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final Media media);
}

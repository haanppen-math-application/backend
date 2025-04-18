package com.hpmath.hpmathcoreapi.dir.service.query;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dto.FileView;
import com.hpmath.hpmathmediadomain.media.entity.Media;

interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final Media media);
}

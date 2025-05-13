package com.hpmath.domain.board.service.query;

import com.hpmath.domain.board.dao.Directory;
import com.hpmath.domain.board.dto.FileView;

interface FileViewMapper {
    FileView create(final Directory directory);
    FileView create(final Media media);
}

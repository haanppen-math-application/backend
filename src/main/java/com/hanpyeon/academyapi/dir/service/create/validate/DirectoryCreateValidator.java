package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dao.Directory;

interface DirectoryCreateValidator {
    void validate(final Directory directory);
}

package com.hpmath.academyapi.dir.service.create.validate;

import com.hpmath.academyapi.dir.dao.Directory;

interface DirectoryCreateValidator {
    void validate(final Directory directory);
}

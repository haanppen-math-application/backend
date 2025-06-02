package com.hpmath.domain.directory.service.create;

import com.hpmath.domain.directory.dao.Directory;

interface DirectoryCreateValidator {
    void validate(final Directory directory);
}

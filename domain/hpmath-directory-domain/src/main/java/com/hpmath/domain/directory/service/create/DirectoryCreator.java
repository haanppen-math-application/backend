package com.hpmath.domain.directory.service.create;

import com.hpmath.domain.directory.dao.Directory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DirectoryCreator {
    @Transactional
    public Directory createDirectory(final Long requestMemberId, final String newDirPath, final boolean canModifyByEveryOne, final boolean canViewByEveryOne) {
        return new Directory(requestMemberId, newDirPath, canModifyByEveryOne, canViewByEveryOne);
    }
}

package com.hpmath.hpmathcoreapi.dir.service.create;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DirectoryCreator {
    @Transactional
    public Directory createDirectory(final Member requestMember, final String newDirPath, final boolean canModifyByEveryOne, final boolean canViewByEveryOne) {
        return new Directory(requestMember, newDirPath, canModifyByEveryOne, canViewByEveryOne);
    }
}

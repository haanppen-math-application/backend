package com.hpmath.academyapi.dir.service.create;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.dir.dao.Directory;
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

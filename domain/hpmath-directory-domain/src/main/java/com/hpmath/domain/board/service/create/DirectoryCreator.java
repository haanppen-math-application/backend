package com.hpmath.domain.board.service.create;

import com.hpmath.domain.board.dao.Directory;
import com.hpmath.domain.member.Member;
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

package com.hpmath.hpmathcoreapi.dir.service.delete;

import com.hpmath.domain.member.Member;
import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DirectoryDeleteCommand {
    private final List<Directory> directories;
    private final Member requestMember;
    private final Boolean deleteChildes;
}

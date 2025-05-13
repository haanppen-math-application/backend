package com.hpmath.domain.directory.service.delete;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.member.Member;
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

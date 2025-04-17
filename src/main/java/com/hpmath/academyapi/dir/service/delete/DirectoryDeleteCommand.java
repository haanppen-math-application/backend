package com.hpmath.academyapi.dir.service.delete;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.dir.dao.Directory;
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

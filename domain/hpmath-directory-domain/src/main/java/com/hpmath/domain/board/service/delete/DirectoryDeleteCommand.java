package com.hpmath.domain.board.service.delete;

import com.hpmath.domain.board.dao.Directory;
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

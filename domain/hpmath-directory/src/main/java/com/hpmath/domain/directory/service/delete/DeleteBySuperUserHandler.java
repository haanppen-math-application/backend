package com.hpmath.domain.directory.service.delete;

import com.hpmath.common.Role;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DeleteBySuperUserHandler implements DeleteDirectoryHandler {
    private final DirectoryRepository directoryRepository;
    @Override
    public Integer process(DirectoryDeleteTargets directoryDeleteCommand) {
        directoryRepository.deleteAll(directoryDeleteCommand.getAllTargets());
        return directoryDeleteCommand.getAllTargets().size();
    }

    @Override
    public boolean applicable(Role role) {
        if (role.equals(Role.MANAGER) || role.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }
}

package com.hpmath.hpmathcoreapi.dir.service.delete.executor;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.dir.service.delete.DirectoryDeleteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteBySuperUserHandler implements DeleteDirectoryHandler {
    private final DeleteDirectoryContentExecutor deleteDirectoryContentExecutor;
    @Override
    public Integer process(DirectoryDeleteCommand directoryDeleteCommand) {
        deleteDirectoryContentExecutor.delete(directoryDeleteCommand.getDirectories());
        return directoryDeleteCommand.getDirectories().size();
    }

    @Override
    public boolean applicable(Role role) {
        if (role.equals(Role.MANAGER) || role.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }
}

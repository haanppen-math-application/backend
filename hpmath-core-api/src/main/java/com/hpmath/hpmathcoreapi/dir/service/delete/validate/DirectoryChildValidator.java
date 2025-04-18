package com.hpmath.hpmathcoreapi.dir.service.delete.validate;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.exception.DirectoryException;
import com.hpmath.hpmathcoreapi.dir.service.delete.DirectoryDeleteCommand;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
class DirectoryChildValidator implements DeleteDirectoryValidator {
    @Override
    public void validate(DirectoryDeleteCommand directoryDeleteCommand) {
        final List<Directory> directories = directoryDeleteCommand.getDirectories();
        if (directories.size() <= 1) {
            return;
        }
        if (directoryDeleteCommand.getDeleteChildes()) {
            return;
        }
        throw new DirectoryException("자식 디렉토리가 있으며, 자식을 지우기 위한 설정 필요", ErrorCode.DIRECTORY_CANNOT_DELETE);
    }
}

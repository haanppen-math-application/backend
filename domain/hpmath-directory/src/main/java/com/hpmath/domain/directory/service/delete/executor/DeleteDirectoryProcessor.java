package com.hpmath.domain.directory.service.delete.executor;

import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.delete.DirectoryDeleteCommand;
import com.hpmath.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDirectoryProcessor {
    private final List<DeleteDirectoryHandler> deleteDirectoryHandlers;

    public Integer delete(DirectoryDeleteCommand directoryDeleteCommand) {
        return deleteDirectoryHandlers.stream()
                .filter(deleteDirectoryExecutor -> deleteDirectoryExecutor.applicable(directoryDeleteCommand.getRequestMemberRole()))
                .findFirst()
                .orElseThrow(() -> new DirectoryException(directoryDeleteCommand.getRequestMemberRole() + "사용자는 디렉토리를 삭제할 수 없습니다.", ErrorCode.DIRECTORY_CANNOT_DELETE))
                .process(directoryDeleteCommand);
    }
}

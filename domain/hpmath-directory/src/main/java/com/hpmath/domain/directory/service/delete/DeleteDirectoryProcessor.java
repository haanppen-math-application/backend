package com.hpmath.domain.directory.service.delete;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.exception.DirectoryException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteDirectoryProcessor {
    private final List<DeleteDirectoryHandler> deleteDirectoryHandlers;

    public Integer delete(DirectoryDeleteTargets directoryDeleteCommand) {
        return deleteDirectoryHandlers.stream()
                .filter(deleteDirectoryExecutor -> deleteDirectoryExecutor.applicable(directoryDeleteCommand.getRequestMemberRole()))
                .findFirst()
                .orElseThrow(() -> new DirectoryException(directoryDeleteCommand.getRequestMemberRole() + "사용자는 디렉토리를 삭제할 수 없습니다.", ErrorCode.DIRECTORY_CANNOT_DELETE))
                .process(directoryDeleteCommand);
    }
}

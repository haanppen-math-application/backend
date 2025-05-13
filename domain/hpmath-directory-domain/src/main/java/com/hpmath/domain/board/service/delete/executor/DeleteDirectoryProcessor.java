package com.hpmath.domain.board.service.delete.executor;

import com.hpmath.domain.board.exception.DirectoryException;
import com.hpmath.domain.board.service.delete.DirectoryDeleteCommand;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDirectoryProcessor {
    private final List<DeleteDirectoryHandler> deleteDirectoryHandlers;

    public Integer delete(DirectoryDeleteCommand directoryDeleteCommand) {
        return deleteDirectoryHandlers.stream()
                .filter(deleteDirectoryExecutor -> deleteDirectoryExecutor.applicable(directoryDeleteCommand.getRequestMember().getRole()))
                .findFirst()
                .orElseThrow(() -> new DirectoryException(directoryDeleteCommand.getRequestMember().getRole() + "사용자는 디렉토리를 삭제할 수 없습니다.", ErrorCode.DIRECTORY_CANNOT_DELETE))
                .process(directoryDeleteCommand);
    }
}

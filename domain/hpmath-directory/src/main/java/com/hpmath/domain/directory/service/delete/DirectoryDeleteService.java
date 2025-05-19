package com.hpmath.domain.directory.service.delete;

import com.hpmath.domain.directory.dto.DeleteDirectoryCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryDeleteService {
    private final DirectoryDeleteCommandCreator directoryDeleteCommandCreator;
    private final DirectoryDeleteManager directoryDeleteManager;

    @Transactional
    public void delete(@Valid final DeleteDirectoryCommand deleteDirectoryDto) {
        final DirectoryDeleteCommand directoryDeleteCommand = directoryDeleteCommandCreator.create(deleteDirectoryDto);
        directoryDeleteManager.delete(directoryDeleteCommand);
    }
}

package com.hpmath.domain.directory.service.delete.validate;

import com.hpmath.domain.directory.service.delete.DirectoryDeleteCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryDeleteValidateManager {
    final List<DeleteDirectoryValidator> deleteDirectoryValidators;

    @Transactional(propagation = Propagation.MANDATORY)
    public void validate(final DirectoryDeleteCommand directoryDeleteCommand) {
        deleteDirectoryValidators.stream()
                .forEach(deleteDirectoryValidator -> deleteDirectoryValidator.validate(directoryDeleteCommand));
    }
}

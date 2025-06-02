package com.hpmath.domain.directory.service.create;

import com.hpmath.domain.directory.dao.Directory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryCreationValidateManager {
    private final List<DirectoryCreateValidator> createValidators;

    @Transactional(propagation = Propagation.MANDATORY)
    public void validate(final Directory directory) {
        createValidators.forEach(dirValidator -> dirValidator.validate(directory));
    }
}

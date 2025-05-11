package com.hpmath.hpmathcoreapi.dir.service.create.validate;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryCreationValidateManager {
    private final List<DirectoryCreateValidator> createValidators;

    @Transactional
    public void validate(final Directory directory) {
        createValidators.forEach(dirValidator -> dirValidator.validate(directory));
    }
}

package com.hpmath.academyapi.dir.service.delete.executor;

import com.hpmath.academyapi.dir.dao.Directory;
import com.hpmath.academyapi.dir.dao.DirectoryRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DeleteDirectoryContentExecutorImpl implements DeleteDirectoryContentExecutor {
    private final DirectoryRepository directoryRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(final Collection<Directory> directories) {
        directories.stream()
                .forEach(directory -> directory.getMedias().clear());
//        mediaService.delete(mediaInfos);
        directoryRepository.deleteAll(directories);
    }
}

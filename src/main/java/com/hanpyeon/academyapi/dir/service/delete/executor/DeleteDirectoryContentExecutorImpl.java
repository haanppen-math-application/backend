package com.hanpyeon.academyapi.dir.service.delete.executor;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
class DeleteDirectoryContentExecutorImpl implements DeleteDirectoryContentExecutor {
//    private final VideoService mediaService;
    private final DirectoryRepository directoryRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(final Collection<Directory> directories) {
//        final List<MediaDeleteDto> mediaInfos = directories.stream()
//                .flatMap(directory -> directory.getMedias().stream()
//                        .map(media -> new MediaDeleteDto(media.getMediaName())))
//                .collect(Collectors.toList());
//        mediaService.delete(mediaInfos);
//        directoryRepository.deleteAll(directories);
    }
}

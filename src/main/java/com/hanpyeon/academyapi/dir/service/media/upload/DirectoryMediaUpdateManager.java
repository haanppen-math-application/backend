package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryMediaUpdateManager {

    private final DirectoryRepository directoryRepository;

    @Transactional
    public void update(final ChunkGroupInfo chunkGroupInfo, final String savedPath) {
        final Directory directory = this.findTargetDirectory(chunkGroupInfo.getDirPath());
        final Media media = create(chunkGroupInfo.getFileName() + chunkGroupInfo.getExtension(), savedPath);
        directory.add(media);
    }

    private Media create(final String fileName, final String savedPath) {
        return new Media(fileName, savedPath);
    }

    private Directory findTargetDirectory(final String resolvedDirPath) {
        final Directory directory = directoryRepository.findDirectoryByPath(resolvedDirPath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        // 업로드 권한 확인로직 추가 필요
        return directory;
    }

}

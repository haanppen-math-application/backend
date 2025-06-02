package com.hpmath.domain.directory.service.query;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryMedia;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.FileView;
import com.hpmath.domain.directory.dto.QueryDirectory;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryQueryService {
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryRepository directoryRepository;
    private final FileViewMapper fileViewMapper;

    /**
     * @param queryDirectoryDto
     * 현재 디렉토리 경로에 존재하는 미디어 파일들에 대한 정보 + 내부 디렉토리들 조회
     * @return
     */
    public List<FileView> queryDirectory(@Valid final QueryDirectory queryDirectoryDto) {
        final List<FileView> fileViews = new ArrayList<>();
        final String resolvedPath = directoryPathFormResolver.resolveToAbsolutePath(queryDirectoryDto.path());

        // 미디어 파일 매핑
        final Directory directory = getDirectory(resolvedPath, queryDirectoryDto.requestMemberId(), queryDirectoryDto.role());
        fileViews.addAll(directory.getMedias().stream()
                .map(DirectoryMedia::getMediaSrc)
                .map(fileViewMapper::create)
                .toList()
        );

        // 포함된 디렉토리들 불러오기
        fileViews.addAll(loadDirectories(resolvedPath).stream().map(fileViewMapper::create).toList());

        return fileViews;
    }

    private Directory getDirectory(final String absolutePath, final Long requestMemberId, final Role role) {
        final Directory directory = directoryRepository.findDirectoryByPath(absolutePath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        if (directory.getCanViewByEveryone()) {
            return directory;
        }
        if (!directory.getOwnerId().equals(requestMemberId) && !isSuperUser(role)) {
            throw new DirectoryException(ErrorCode.ITS_NOT_YOUR_DIRECTORY);
        }
        return directory;
    }

    private boolean isSuperUser(final Role role) {
        if (role.equals(Role.MANAGER) || role.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    private List<Directory> loadDirectories(final String targetPath) {
        return directoryRepository.queryDirectoriesOneDepth(targetPath);
    }
}

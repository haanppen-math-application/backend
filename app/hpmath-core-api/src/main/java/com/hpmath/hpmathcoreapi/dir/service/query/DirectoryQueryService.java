package com.hpmath.hpmathcoreapi.dir.service.query;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dao.DirectoryRepository;
import com.hpmath.hpmathcoreapi.dir.dto.FileView;
import com.hpmath.hpmathcoreapi.dir.dto.QueryDirectory;
import com.hpmath.hpmathcoreapi.dir.exception.DirectoryException;
import com.hpmath.hpmathcoreapi.dir.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryQueryService {

    private final MemberRepository memberRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryRepository directoryRepository;
    private final FileViewMapper fileViewMapper;


    /**
     * @param queryDirectoryDto
     * 현재 디렉토리 경로에 존재하는 미디어 파일들에 대한 정보 + 내부 디렉토리들 조회
     * @return
     */
    public List<FileView> queryDirectory(final QueryDirectory queryDirectoryDto) {
        final List<FileView> fileViews = new ArrayList<>();
        final String resolvedPath = directoryPathFormResolver.resolveToAbsolutePath(queryDirectoryDto.path());

        // 미디어 파일 매핑
        final Directory directory = getDirectory(resolvedPath, queryDirectoryDto.requestMemberId());
        fileViews.addAll(directory.getMedias().stream().map(fileViewMapper::create).toList());

        // 포함된 디렉토리들 불러오기
        fileViews.addAll(loadDirectories(resolvedPath).stream().map(fileViewMapper::create).toList());

        return fileViews;
    }

    private Directory getDirectory(final String absolutePath, final Long requestMemberId) {
        final Directory directory = directoryRepository.findDirectoryByPath(absolutePath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        if (directory.getCanViewByEveryone()) {
            return directory;
        }
        if (!directory.getOwner().getId().equals(requestMemberId) && !isSuperUser(requestMemberId)) {
            throw new DirectoryException(ErrorCode.ITS_NOT_YOUR_DIRECTORY);
        }
        return directory;
    }

    private boolean isSuperUser(final Long memberId) {
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException("멤버 찾을 수 없음", ErrorCode.CANNOT_FIND_USER));
        final Role role = member.getRole();
        if (role.equals(Role.MANAGER) || role.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    private List<Directory> loadDirectories(final String targetPath) {
        return directoryRepository.queryDirectoriesOneDepth(targetPath);
    }
}

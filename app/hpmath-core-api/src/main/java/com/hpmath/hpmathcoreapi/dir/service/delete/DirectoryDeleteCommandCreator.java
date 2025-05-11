package com.hpmath.hpmathcoreapi.dir.service.delete;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dao.DirectoryRepository;
import com.hpmath.hpmathcoreapi.dir.dto.DeleteDirectoryDto;
import com.hpmath.hpmathcoreapi.dir.exception.DirectoryException;
import com.hpmath.hpmathcoreapi.dir.service.form.resolver.DirectoryPathFormResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DirectoryDeleteCommandCreator {

    private final DirectoryRepository directoryRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final MemberRepository memberRepository;

    public DirectoryDeleteCommand create(final DeleteDirectoryDto deleteDirectoryDto) {
        final List<Directory> targetDirectories = getAssociatedDirectories(deleteDirectoryDto.getTargetPath());
        final Member requestMember = getRequestMember(deleteDirectoryDto.getRequestMemberId());
        return new DirectoryDeleteCommand(targetDirectories, requestMember, deleteDirectoryDto.getDeleteChildes());
    }

    private List<Directory> getAssociatedDirectories(final String targetPath) {
        final String path = directoryPathFormResolver.resolveToAbsolutePath(targetPath);
        directoryRepository.findDirectoryByPath(path)
                .orElseThrow(() -> new DirectoryException(path + " : 디렉토리를 찾을 수 없습니다",ErrorCode.NOT_EXIST_DIRECTORY));
        final List<Directory> directories = directoryRepository.queryChildDirectories(path);
        return directories;
    }

    private Member getRequestMember(final Long requestMemberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(requestMemberId)
                .orElseThrow(() -> new DirectoryException(ErrorCode.CANNOT_FIND_USER));
    }

}

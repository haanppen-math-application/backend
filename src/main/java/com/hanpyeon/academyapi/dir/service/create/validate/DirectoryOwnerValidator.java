package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectoryOwnerValidator implements DirectoryCreateValidator {
    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void validate(final CreateDirectoryDto createDirectoryDto) {
        final Directory targetDirectory = directoryRepository.findDirectoryByPath(createDirectoryDto.directoryPath())
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
        final Member requestMember = memberRepository.findMemberByIdAndRemovedIsFalse(createDirectoryDto.ownerId())
                .orElseThrow(() -> new DirectoryException(ErrorCode.CANNOT_CREATE_DIRECTORY_WITH_THIS_USER));
        isRequestMemberIsOwner(targetDirectory, requestMember);
    }

    private void isRequestMemberIsOwner(final Directory targetDirectory, final Member requestMember) {
        if (isOverManager(requestMember)) {
            return;
        }
        // 이전 디렉토리의 소유자가 맞는지 확인
        if (!targetDirectory.getId().equals(requestMember.getId())) {
            throw new DirectoryException(ErrorCode.ITS_NOT_YOUR_DIRECTORY);
        }
    }

    private boolean isOverManager(final Member requestMember) {
        final Role memberRole = requestMember.getRole();
        if (memberRole.equals(Role.MANAGER) || memberRole.equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }
}

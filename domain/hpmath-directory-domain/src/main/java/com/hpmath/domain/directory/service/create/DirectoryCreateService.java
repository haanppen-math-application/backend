package com.hpmath.domain.directory.service.create;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.CreateDirectoryCommand;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.create.validate.DirectoryCreationValidateManager;
import com.hpmath.domain.directory.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryCreateService {
    private final MemberRepository memberRepository;
    private final DirectoryCreator directoryCreator;
    private final DirectoryRepository directoryRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryCreationValidateManager directoryCreationValidateManager;

    @Transactional
    public void addNewDirectory(final CreateDirectoryCommand createDirectoryDto) {
        final String absoluteDirPath = getAbsoluteDirPath(createDirectoryDto);
        final Member member = findMember(createDirectoryDto.ownerId());

        final Directory directory = directoryCreator.createDirectory(member, absoluteDirPath, createDirectoryDto.canModifyByEveryone(), createDirectoryDto.canViewByEveryone());
        directoryCreationValidateManager.validate(directory);
        directoryRepository.save(directory);
    }

    private String getAbsoluteDirPath(CreateDirectoryCommand createDirectoryDto) {
        final String parentDirPath = directoryPathFormResolver.resolveToAbsolutePath(createDirectoryDto.parentDirPath());
        return directoryPathFormResolver.resolveToAbsolutePath(parentDirPath, createDirectoryDto.directoryName());
    }

    private Member findMember(final Long ownerId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(ownerId)
                .orElseThrow(() -> new DirectoryException(ErrorCode.CANNOT_FIND_USER));
    }
}

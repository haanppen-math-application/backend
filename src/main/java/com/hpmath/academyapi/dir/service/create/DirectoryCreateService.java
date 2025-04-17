package com.hpmath.academyapi.dir.service.create;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.dir.dao.Directory;
import com.hpmath.academyapi.dir.dao.DirectoryRepository;
import com.hpmath.academyapi.dir.dto.CreateDirectoryCommand;
import com.hpmath.academyapi.dir.exception.DirectoryException;
import com.hpmath.academyapi.dir.service.create.validate.DirectoryCreationValidateManager;
import com.hpmath.academyapi.dir.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class DirectoryCreateService {
    private final MemberRepository memberRepository;
    private final DirectoryCreator directoryCreator;
    private final DirectoryRepository directoryRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryCreationValidateManager directoryCreationValidateManager;

    @Transactional
    public void addNewDirectory(@Validated final CreateDirectoryCommand createDirectoryDto) {
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

package com.hpmath.domain.directory.service.delete;

import com.hpmath.common.Role;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DeleteByTeacherHandler implements DeleteDirectoryHandler {
    private final DescendingSortByDepthResolver descendingSoryByDepthResolver;
    private final DirectoryRepository directoryRepository;

    @Override
    public Integer process(DirectoryDeleteTargets directoryDeleteCommand) {
        final Collection<Directory> deletableDirectories = getRemovableDirectories(directoryDeleteCommand);

        directoryRepository.deleteAll(deletableDirectories);
        return deletableDirectories.size();
    }

    private Collection<Directory> getRemovableDirectories(DirectoryDeleteTargets directoryDeleteCommand) {
        final Set<Directory> unDeletableDirectories = getUnDeletableDirectories(directoryDeleteCommand.getAllTargets(), directoryDeleteCommand.getRequestMemberId());

        return directoryDeleteCommand.getAllTargets().stream().filter(directory -> !unDeletableDirectories.contains(directory))
                .collect(Collectors.toList());
    }

    private Set<Directory> getUnDeletableDirectories(final List<Directory> allTargets, final Long requestmemberId) {
        final List<Directory> descendingSortedDirectories = descendingSoryByDepthResolver.getDecsendingList(allTargets);
        final Set<Directory> unDeletableDirectories = new HashSet<>();

        for (final Directory directory : descendingSortedDirectories) {
            if (!isOwner(directory.getOwnerId(), requestmemberId)) {
                unDeletableDirectories.add(directory);
                continue;
            }
            if (doesChildRemain(unDeletableDirectories, directory.getPath())) {
                unDeletableDirectories.add(directory);
            }
        }
        return unDeletableDirectories;
    }

    private boolean isOwner(final Long ownerId, final Long requestMemberId) {
        return ownerId.equals(requestMemberId);
    }

    private boolean doesChildRemain(final Set<Directory> undeletedDirectories, final String currPath) {
        return undeletedDirectories.stream()
                .anyMatch(directory -> directory.getPath().startsWith(currPath));
    }

    @Override
    public boolean applicable(Role role) {
        if (role.equals(Role.TEACHER)) {
            return true;
        }
        return false;
    }
}

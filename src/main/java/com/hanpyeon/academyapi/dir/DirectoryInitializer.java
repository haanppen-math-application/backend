package com.hanpyeon.academyapi.dir;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectoryInitializer {
    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    private void init() {
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(1l).orElseThrow();
        directoryRepository.save(new Directory(member, "/", false));
        directoryRepository.save(new Directory(member, "/teachers/", true));
    }
}

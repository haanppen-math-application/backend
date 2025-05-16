package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hpmath.common.Role;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OnlineVideoSequenceUpdateServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineVideoRepository onlineVideoRepository;

    @Autowired
    private OnlineVideoSequenceUpdateService onlineVideoSequenceUpdateService;

    @Test
    void test() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final List<OnlineVideo> onlineVideos = new ArrayList<>();
        for (int i = 0; i < 100 ; i ++) {
            onlineVideos.add(new OnlineVideo(onlineCourse, null, "test" + i, true, i + 1));
        }
        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        onlineVideoRepository.saveAll(onlineVideos);

        final Long memberId = member.getId();
        final Role role = member.getRole();

        final Long courseId = onlineCourse.getId();
        final Long targetVideoId = onlineVideos.get(4).getId();
        final Integer nextSequence = 10;

        entityManager.flush();
        entityManager.clear();

        onlineVideoSequenceUpdateService.updateSequence(new UpdateOnlineVideoSequenceCommand(
                courseId,
                targetVideoId,
                nextSequence,
                memberId,
                role
        ));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(onlineVideoRepository.findById(targetVideoId).get().getVideoSequence(), nextSequence);
    }
}
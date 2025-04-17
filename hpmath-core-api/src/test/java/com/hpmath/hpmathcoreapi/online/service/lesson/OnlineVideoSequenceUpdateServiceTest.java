package com.hpmath.hpmathcoreapi.online.service.lesson;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideo;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideoRepository;
import com.hpmath.hpmathcoreapi.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hpmath.hpmathcoreapi.security.Role;
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
@Transactional
@ActiveProfiles("test")
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
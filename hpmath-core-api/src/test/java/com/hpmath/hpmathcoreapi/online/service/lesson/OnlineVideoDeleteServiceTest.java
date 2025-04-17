package com.hpmath.hpmathcoreapi.online.service.lesson;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideo;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideoRepository;
import com.hpmath.hpmathcoreapi.online.dto.DeleteOnlineCourseVideoCommand;
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
@ActiveProfiles("test")
class OnlineVideoDeleteServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OnlineVideoDeleteService onlineVideoDeleteService;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineVideoRepository onlineVideoRepository;

    @Transactional
    @Test
    void 비디오_삭제_후_순서_업데이트_테스트() {
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

        entityManager.flush();
        entityManager.clear();

        onlineVideoDeleteService.deleteOnlineVideo(new DeleteOnlineCourseVideoCommand(onlineCourse.getId(), onlineVideos.get(5).getId(), member.getId(), member.getRole()));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertTrue(onlineVideoRepository.findAll().stream()
                .noneMatch(onlineVideo -> onlineVideo.getVideoSequence() == 100));
    }
}
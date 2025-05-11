package com.hpmath.hpmathcoreapi.online.service.lesson;

import com.hpmath.HpmathCoreApiApplication;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineVideo;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineVideoCommand;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineVideoCommand.OnlineVideoCommand;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = HpmathCoreApiApplication.class)
@ActiveProfiles("test")
@Transactional
class OnlineCourseVideoServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private OnlineVideoRegisterService onlineCourseVideoService;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;

    @Test
    void videoAddServiceTest() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final Media media = new Media("test", "src", member.getId());

        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        mediaRepository.save(media);

        entityManager.flush();
        entityManager.clear();

        final AddOnlineVideoCommand addOnlineVideoCommand = new AddOnlineVideoCommand(onlineCourse.getId(), new OnlineVideoCommand(media.getSrc(), true),
                member.getId(), member.getRole());

        onlineCourseVideoService.addOnlineVideo(addOnlineVideoCommand);

        entityManager.flush();
        entityManager.clear();

        final OnlineVideo onlineVideo = onlineCourseRepository.loadCourseAndVideosAndTeacherByCourseId(onlineCourse.getId())
                .orElseThrow()
                .getVideos().get(0);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(onlineVideo.getPreview(), true);
            Assertions.assertEquals(onlineVideo.getMedia().getId(), media.getId());
            Assertions.assertEquals(onlineVideo.getVideoSequence(), 1);
        });
    }
}
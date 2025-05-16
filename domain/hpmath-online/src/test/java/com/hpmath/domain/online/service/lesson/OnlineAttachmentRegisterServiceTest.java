package com.hpmath.domain.online.service.lesson;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineVideo;
import com.hpmath.domain.online.dao.OnlineVideoRepository;
import com.hpmath.domain.online.dto.RegisterOnlineVideoAttachmentCommand;
import com.hpmath.common.Role;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OnlineAttachmentRegisterServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private OnlineCourseRepository onlineCourseRepository;
    @Autowired
    private OnlineVideoRepository onlineVideoRepository;
    @Autowired
    private OnlineAttachmentRegisterService onlineAttachmentRegisterService;

    @Test
    void 수업_등록_쿼리_테스트() {
        final Member member = Member.builder()
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("test")
                .build();
        final OnlineCourse onlineCourse = new OnlineCourse(member, "test");
        final Media media = new Media("test", "src", member.getId());
        final OnlineVideo onlineVideo = new OnlineVideo(onlineCourse, media, "name", true, 1);

        memberRepository.save(member);
        onlineCourseRepository.save(onlineCourse);
        mediaRepository.save(media);
        onlineVideoRepository.save(onlineVideo);

        final String mediaId = media.getSrc();
        final Long onlineCourseId = onlineCourse.getId();
        final Long onlineVideoId = onlineVideo.getId();
        final Long memberId = member.getId();
        final Role memberRole = member.getRole();

        entityManager.flush();
        entityManager.clear();

        onlineAttachmentRegisterService.register(new RegisterOnlineVideoAttachmentCommand(
                "test",
                mediaId,
                onlineCourseId,
                onlineVideoId,
                memberId,
                memberRole
        ));

        entityManager.flush();
        entityManager.clear();
    }
}
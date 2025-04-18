package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;
import com.hpmath.hpmathcoreapi.course.domain.MemoMediaContainer;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import com.hpmath.hpmathmediadomain.media.repository.MediaRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class UpdateMemoMediaContainerAdapterTest {

    @Autowired
    private MemoMediaRepository memoMediaRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemoRepository memoRepository;
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UpdateMemoMediaContainerAdapter containerAdapter;


    @Test
    @Transactional
    void testSequenceUpdate() {
        final Long memoId = 1l;
        final Media media1 = new Media("test", "1", null);
        final Media media2 = new Media("test", "2", null);
        final Media media3 = new Media("test", "3", null);

        mediaRepository.saveAll(List.of(media1, media2, media3));


        memoMediaRepository.saveAll(List.of(
                com.hpmath.hpmathcoreapi.course.entity.MemoMedia.of(null, media1, 1),
                com.hpmath.hpmathcoreapi.course.entity.MemoMedia.of(null, media2, 2),
                com.hpmath.hpmathcoreapi.course.entity.MemoMedia.of(null, media3, 3))
        );

        final MemoMediaContainer container = MemoMediaContainer.of(
                List.of(
                        MemoMedia.createByEntity(1l, "", "1", 1L, 3),
                        MemoMedia.createByEntity(2l, "", "2", 1L, 2),
                        MemoMedia.createByEntity(3l, "", "3", 1L, 1)
                ), memoId);

        containerAdapter.save(container);

        Assertions.assertThat(memoMediaRepository.findById(1l).orElseThrow().getSequence()).isEqualTo(3);
        Assertions.assertThat(memoMediaRepository.findById(2l).orElseThrow().getSequence()).isEqualTo(2);
        Assertions.assertThat(memoMediaRepository.findById(3l).orElseThrow().getSequence()).isEqualTo(1);
    }
}
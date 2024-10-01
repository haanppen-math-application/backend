package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class UpdateMemoMediaContainerAdapterTest {

    @Autowired
    private MemoMediaRepository memoMediaRepository;
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
        final Memo memo = new Memo(new Course(), null, "", "");
        memoRepository.save(memo);

        final Media media1 = new Media("test", "1");
        final Media media2 = new Media("test", "2");
        final Media media3 = new Media("test", "3");

        mediaRepository.saveAll(List.of(media1, media2, media3));


        memoMediaRepository.saveAll(List.of(
                com.hanpyeon.academyapi.course.adapter.out.MemoMedia.of(memo, media1, 1),
                com.hanpyeon.academyapi.course.adapter.out.MemoMedia.of(memo, media2, 2),
                com.hanpyeon.academyapi.course.adapter.out.MemoMedia.of(memo, media3, 3))
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
package com.hpmath.hpmathcoreapi.media.repository;

import com.hpmath.hpmathcoreapi.media.entity.Image;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    @Transactional
    void 이미지_찾기_테스트() {
        final String imageName1 = "test1";
        final String imageName2 = "test2";
        final String imageName3 = "test3";
        imageRepository.saveAll(List.of(new Image(imageName1), new Image(imageName2), new Image(imageName3)));
        Assertions.assertThat(imageRepository.existsAllBySrcIn(List.of(imageName1)))
                .isTrue();
    }


}
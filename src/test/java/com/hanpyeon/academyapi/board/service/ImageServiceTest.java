package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.repository.ImageRepository;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.media.service.UploadFile;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import com.hanpyeon.academyapi.media.validator.UploadImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @Mock
    ImageRepository imageRepository;
    @Mock
    MediaStorage mediaStorage;
    @Mock
    UploadImageValidator uploadImageValidator;
    @Mock
    MediaMapper mediaMapper;

    ImageService imageService;

    @BeforeEach
    void init() {
        this.imageService = new ImageService(imageRepository, mediaStorage, uploadImageValidator, mediaMapper);
    }

    @Test
    void 이미지_없음_처리_테스트() {
        assertThat(imageService.saveImage(null))
                .isEmpty();
        assertThat(imageService.saveImage(Collections.emptyList()))
                .isEmpty();
    }

//    거짓 양성을 피하고 리팩터링 내성을 향상 시키기 위해 구현 세부사항이 아닌 결과에 집중하라
    @Test
    void 파일_존재시_등록_테스트() {
        Image image = Mockito.mock(Image.class);
        MultipartFile multipartFile = new MockMultipartFile("name", "temp".getBytes());
        UploadFile uploadFile = Mockito.mock(UploadFile.class);
        List<MultipartFile> files = List.of(multipartFile);


        Mockito.when(mediaMapper.createUploadFile(Mockito.any()))
                        .thenReturn(uploadFile);
        Mockito.when(mediaMapper.createImage(Mockito.anyString()))
                        .thenReturn(image);
        Mockito.when(uploadFile.validateWith(uploadImageValidator))
                        .thenReturn(uploadFile);
        Mockito.when(uploadFile.uploadTo(mediaStorage))
                        .thenReturn(Mockito.anyString());

        imageService.saveImage(files);

        Mockito.verify(imageRepository).saveAll(Mockito.any());
    }
}
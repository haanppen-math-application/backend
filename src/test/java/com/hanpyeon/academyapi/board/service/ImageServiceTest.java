package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.mapper.MediaMapper;
import com.hanpyeon.academyapi.board.repository.ImageRepository;
import com.hanpyeon.academyapi.board.service.storage.MediaStorage;
import com.hanpyeon.academyapi.board.service.validator.UploadImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void empty_imageFiles_Params_FailTest() {
        assertThat(imageService.saveImage(null))
                .isEmpty();
        assertThat(imageService.saveImage(Collections.emptyList()))
                .isEmpty();
    }

    @Test
    void process_Test() {
        Image image = Mockito.mock(Image.class);
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        UploadFile uploadFile = Mockito.mock(UploadFile.class);

        List<MultipartFile> files = List.of(multipartFile);
        List<Image> expectedImage = List.of(image);


        Mockito.when(mediaMapper.createUploadFile(Mockito.any()))
                        .thenReturn(uploadFile);
        Mockito.when(mediaMapper.createImage(Mockito.anyString()))
                        .thenReturn(image);
        Mockito.when(uploadFile.validateWith(uploadImageValidator))
                        .thenReturn(uploadFile);
        Mockito.when(uploadFile.uploadTo(mediaStorage))
                        .thenReturn(Mockito.anyString());

        imageService.saveImage(files);

        Mockito.verify(uploadFile).validateWith(uploadImageValidator);
        Mockito.verify(uploadFile).uploadTo(mediaStorage);
    }
}
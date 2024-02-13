package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @Mock
    ImageRepository imageRepository;
    @Mock
    StorageService storageService;

    ImageService imageService;

    @BeforeEach
    void init() {
        this.imageService = new ImageService(imageRepository, storageService);
    }

    @Test
    void empty_imageFiles_Params_FailTest() {
        assertThat(imageService.saveImage(null))
                .isEmpty();
        assertThat(imageService.saveImage(Collections.emptyList()))
                .isEmpty();
    }

    @Test
    void contains_null_imageFiles_Params_FailTest() {
        ArrayList<MultipartFile> files = new ArrayList<>();
        files.add(null);
        files.add(Mockito.mock(MultipartFile.class));

        assertThatThrownBy(() -> imageService.saveImage(files))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void succeed_Test() {
        List<MultipartFile> files = List.of(Mockito.mock(MultipartFile.class), Mockito.mock(MultipartFile.class));
        List<Image> expectedImage = List.of(Mockito.mock(Image.class), Mockito.mock(Image.class));

        Mockito.lenient().when(storageService.storeMultiPartFiles(files))
                .thenReturn(expectedImage);
        Mockito.lenient().when(imageRepository.saveAll(expectedImage))
                .thenReturn(expectedImage);

        imageService.saveImage(files);

        Mockito.verify(imageRepository).saveAll(expectedImage);
    }
}
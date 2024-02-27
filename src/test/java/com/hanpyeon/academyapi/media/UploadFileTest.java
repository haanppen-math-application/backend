package com.hanpyeon.academyapi.media;

import com.hanpyeon.academyapi.media.exception.InvalidUploadFileException;
import com.hanpyeon.academyapi.media.service.UploadFile;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import com.hanpyeon.academyapi.media.validator.UploadFileValidator;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UploadFileTest {
    @Mock
    UploadFileValidator uploadFileValidator;
    @Mock
    MediaStorage mediaStorage;

    @Test
    void 확장자_없음_실패_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejongpng", ContentType.IMAGE_PNG.toString(), "image".getBytes());

        assertThatThrownBy(() -> new UploadFile(multipartFile))
                .isInstanceOf(InvalidUploadFileException.class);
    }

    @Test
    void 확장자_있음_성공_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());

        assertThatNoException().isThrownBy(() -> new UploadFile(multipartFile));
    }
    @Test
    void Validator_성공_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());
        Mockito.when(uploadFileValidator.validate(multipartFile))
                .thenReturn(true);

        UploadFile uploadFile = new UploadFile(multipartFile);
        assertThat(uploadFile.validateWith(uploadFileValidator))
                .isEqualTo(uploadFile);

    }
    @Test
    void Validator_실패_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());
        Mockito.when(uploadFileValidator.validate(multipartFile))
                .thenReturn(false);

        UploadFile uploadFile = new UploadFile(multipartFile);
        assertThatThrownBy(() -> uploadFile.validateWith(uploadFileValidator))
                .isInstanceOf(InvalidUploadFileException.class);

    }

}
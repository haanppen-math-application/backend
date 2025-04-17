package com.hpmath.academyapi.media;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hpmath.academyapi.media.exception.InvalidUploadFileException;
import com.hpmath.academyapi.media.storage.uploadfile.ImageUploadFile;
import com.hpmath.academyapi.media.storage.MediaStorage;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class UploadFileTest {
    @Mock
    MediaStorage mediaStorage;

    @Test
    void 확장자_없음_실패_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejongpng", ContentType.IMAGE_PNG.toString(), "image".getBytes());

        assertThatThrownBy(() -> new ImageUploadFile(multipartFile))
                .isInstanceOf(InvalidUploadFileException.class);
    }

    @Test
    void 확장자_있음_성공_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());

        assertThatNoException().isThrownBy(() -> new ImageUploadFile(multipartFile));
    }
//    @Test
//    void Validator_성공_테스트() {
//        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());
//        Mockito.when(uploadFileValidator.validate(new ImageUploadFile(multipartFile)))
//                .thenReturn(true);
//
//        ImageUploadFile uploadFile = new ImageUploadFile(multipartFile);
//        assertThat(validateWith(uploadFileValidator))
//                .isEqualTo(uploadFile);

//    }
//    @Test
//    void Validator_실패_테스트() {
//        MockMultipartFile multipartFile = new MockMultipartFile("image", "heejong.png", ContentType.IMAGE_PNG.toString(), "image".getBytes());
//        Mockito.when(uploadFileValidator.validate(multipartFile))
//                .thenReturn(false);
//
//        ImageUploadFile uploadFile = new ImageUploadFile(multipartFile);
//        assertThatThrownBy(() -> uploadFile.validateWith(uploadFileValidator))
//                .isInstanceOf(InvalidUploadFileException.class);
//
//    }

}
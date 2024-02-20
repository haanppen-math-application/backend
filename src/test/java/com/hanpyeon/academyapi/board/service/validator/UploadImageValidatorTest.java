package com.hanpyeon.academyapi.board.service.validator;

import com.hanpyeon.academyapi.media.validator.UploadImageValidator;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class UploadImageValidatorTest {
    @Test
    void 파일_유효성_성공_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "heejong.png",
                ContentType.IMAGE_PNG.toString(),
                "image".getBytes());
        UploadImageValidator uploadImageValidator = new UploadImageValidator();

        assertThat(uploadImageValidator.validate(multipartFile))
                .isTrue();
    }
    @Test
    void 파일_유효성_실패_테스트() {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "heejong.png",
                ContentType.IMAGE_PNG.toString(),
                (byte[]) null);
        UploadImageValidator uploadImageValidator = new UploadImageValidator();

        assertThat(uploadImageValidator.validate(multipartFile))
                .isFalse();
    }
}
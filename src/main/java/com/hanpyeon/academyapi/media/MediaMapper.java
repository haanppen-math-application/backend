package com.hanpyeon.academyapi.media;

import com.hanpyeon.academyapi.media.dto.ImageUrlDto;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.storage.uploadfile.AsyncImageUploadFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MediaMapper {
    public AsyncImageUploadFile createImageUploadFile(final MultipartFile multipartFile) {
        return new AsyncImageUploadFile(multipartFile);
    }

    public Image createImage(final String imageSrc) {
        return new Image(imageSrc);
    }

    public ImageUrlDto createImageUrlDto(final Image image) {
        return ImageUrlDto.builder()
                .imageUrl(image.getSrc())
                .build();
    }
}

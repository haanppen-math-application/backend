package com.hpmath.hpmathcoreapi.media;

import com.hpmath.hpmathcoreapi.media.dto.ImageUrlDto;
import com.hpmath.hpmathcoreapi.media.entity.Image;
import com.hpmath.hpmathcoreapi.media.storage.uploadfile.AsyncImageUploadFile;
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

package com.hpmath.domain.media.service;

import com.hpmath.domain.media.dto.ImageUrlDto;
import com.hpmath.domain.media.dto.MediaDto;
import com.hpmath.domain.media.storage.MediaStorage;
import com.hpmath.domain.media.storage.AsyncMediaStorage;
import com.hpmath.domain.media.storage.uploadfile.AsyncImageUploadFile;
import com.hpmath.domain.media.storage.uploadfile.ImageUploadFile;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Validated
public class ImageService {
    private final MediaStorage mediaStorage;
    private final AsyncMediaStorage asyncMediaStorage;

    /**
     * async 하게 업로드
     *
     * @param imageFile not null
     * @return never returns null
     */
    public ImageUrlDto uploadImageAsync(@NotNull final MultipartFile imageFile) {
        final AsyncImageUploadFile asyncImageUploadFile = new AsyncImageUploadFile(imageFile);
        asyncMediaStorage.store(asyncImageUploadFile);

        return new ImageUrlDto(asyncImageUploadFile.getUniqueFileName());
    }

    public ImageUrlDto uploadImage(@NotNull final MultipartFile imageFile) {
        final ImageUploadFile imageUploadFile = new ImageUploadFile(imageFile);
        mediaStorage.store(imageUploadFile);

        return new ImageUrlDto(imageUploadFile.getUniqueFileName());
    }

    public void removeImage(@NotNull final String imageSrc) {
        mediaStorage.remove(imageSrc);
    }

    public MediaDto loadImage(@NotNull final String imageSrc) {
        return mediaStorage.loadFile(imageSrc);
    }
}

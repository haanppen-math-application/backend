package com.hanpyeon.academyapi.media.service;

import com.amazonaws.services.s3.transfer.Upload;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.dto.ImageUrlDto;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.exception.MediaStoreException;
import com.hanpyeon.academyapi.media.repository.ImageRepository;
import com.hanpyeon.academyapi.media.storage.StorageAsyncDecorator;
import com.hanpyeon.academyapi.media.storage.AsyncUploadFile;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
@WarnLoggable
public class ImageService {

    private final ImageRepository imageRepository;
    private final MediaStorage mediaStorage;
    private final MediaMapper mediaMapper;
    private final StorageAsyncDecorator storageAsyncDecorator;

    /**
     * MultipartFile 을 처리하고, ImageRepository 엔티티 저장하는 역할을 수행합니다.
     *
     * @param imageFiles empty일 경우, 빈 컬렉션을 리턴합니다.
     * @return images, never returns null
     */
    @Transactional
    public List<Image> saveImage(final List<MultipartFile> imageFiles) {
        if (!checkImagesPresence(imageFiles)) {
            return Collections.emptyList();
        }
        final List<AsyncImageUploadFile> uploadFiles = imageFiles.stream()
                .filter(multipartFile -> !multipartFile.isEmpty())
                .map(mediaMapper::createImageUploadFile)
                .toList();
        uploadFiles.forEach(storageAsyncDecorator::store);
        return saveImageNames(
                uploadFiles.stream()
                        .map(UploadFile::getUniqueFileName)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public List<Image> loadImages(final List<String> imageSrcs) {
        return imageRepository.findAllBySrcIn(imageSrcs);
    }

    @Transactional(readOnly = true)
    public Boolean isExists(final List<String> imageSources) {
        return imageRepository.existsAllBySrcIn(imageSources);
    }

    @Transactional
    public ImageUrlDto saveSingleImage(final MultipartFile image) {
        return this.saveImage(List.of(image)).stream()
                .map(imageEntity -> new ImageUrlDto(imageEntity.getSrc()))
                .findAny()
                .orElseThrow(() -> new MediaStoreException(ErrorCode.MEDIA_STORE_EXCEPTION));
    }

    @Transactional
    public void removeImage(final List<Image> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        images.stream()
                .map(image -> image.getSrc())
                .forEach(mediaStorage::remove);
        imageRepository.deleteAll(images);
    }

    public MediaDto loadImage(final String imageName) {
        return mediaStorage.loadFile(imageName);
    }

    private boolean checkImagesPresence(final List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) {
            return false;
        }
        return true;
    }

    private List<Image> saveImageNames(List<String> imageNames) {
        List<Image> images = imageNames.stream()
                .map(mediaMapper::createImage)
                .toList();
        return imageRepository.saveAll(images);
    }
}

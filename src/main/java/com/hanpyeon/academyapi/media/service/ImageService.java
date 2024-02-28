package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.dto.MediaDto;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.repository.ImageRepository;
import com.hanpyeon.academyapi.media.storage.MediaStorage;
import com.hanpyeon.academyapi.media.validator.UploadImageValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
@WarnLoggable
public class ImageService {

    private final ImageRepository imageRepository;
    private final MediaStorage mediaStorage;
    private final UploadImageValidator uploadImageValidator;
    private final MediaMapper mediaMapper;

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
        return saveImageNames(
                imageFiles.stream()
                        .filter(multipartFile -> !multipartFile.isEmpty())
                        .map(mediaMapper::createUploadFile)
                        .map(uploadFile -> uploadFile.validateWith(uploadImageValidator))
                        .map(uploadFile -> uploadFile.uploadTo(mediaStorage))
                        .toList()
        );
    }

    @Transactional
    public void updateImage(final Comment comment, final List<MultipartFile> images) {
        comment.getImages().stream()
                .forEach(image -> mediaStorage.remove(image.getSrc()));
        comment.changeImagesTo(saveImage(images));
    }

    @Transactional
    public void removeImage(final List<Image> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        images.stream()
                .map(image -> image.getSrc())
                .forEach(mediaStorage::remove);
        images.stream()
                .forEach(imageRepository::delete);
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

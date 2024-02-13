package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final StorageService storageService;

    /**
     * MultipartFile 을 처리하고, ImageRepository 엔티티 저장하는 역할을 수행합니다.
     * @param imageFiles empty일 경우, 빈 컬렉션을 리턴합니다.
     * @return images, never returns null
     */
    public List<Image> saveImage(final List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) {
            return Collections.emptyList();
        }
        validate(imageFiles);
        List<Image> images = storageService.storeMultiPartFiles(imageFiles);
        return imageRepository.saveAll(images);
    }


    private void validate(List<MultipartFile> files) {
        if (files.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}

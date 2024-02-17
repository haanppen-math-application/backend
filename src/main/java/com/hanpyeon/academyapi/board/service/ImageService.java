package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.dto.ImageDto;
import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.repository.ImageRepository;
import com.hanpyeon.academyapi.board.service.storage.MediaStorage;
import com.hanpyeon.academyapi.board.service.storage.UploadFile;
import com.hanpyeon.academyapi.board.service.validator.UploadImageValidator;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final MediaStorage mediaStorage;
    private final UploadImageValidator uploadImageValidator;

    /**
     * MultipartFile 을 처리하고, ImageRepository 엔티티 저장하는 역할을 수행합니다.
     *
     * @param imageFiles empty일 경우, 빈 컬렉션을 리턴합니다.
     * @return images, never returns null
     */
    public List<Image> saveImage(final List<MultipartFile> imageFiles) {
        if (!checkImagesPresence(imageFiles)) {
            return Collections.emptyList();
        }
        List<String> imageNames = new ArrayList<>();
        for (MultipartFile multipartFile : imageFiles) {
            UploadFile uploadFile = new UploadFile(multipartFile);
            uploadFile.validateWith(uploadImageValidator);

            imageNames.add(uploadFile.uploadTo(mediaStorage));
        }
        return saveImageNames(imageNames);
    }
    public ImageDto loadImage(final String imageName) {
        Resource resource = mediaStorage.loadFile(imageName);
        try {
            return new ImageDto(resource, MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkImagesPresence(final List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) {
            return false;
        }
        return true;
    }

    private List<Image> saveImageNames(List<String> imageNames) {
        List<Image> images = imageNames.stream()
                .map(Image::new)
                .toList();
        return imageRepository.saveAll(images);
    }
}

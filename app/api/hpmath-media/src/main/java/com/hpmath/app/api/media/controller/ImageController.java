package com.hpmath.app.api.media.controller;

import com.hpmath.domain.media.dto.ImageUrlDto;
import com.hpmath.domain.media.dto.MediaDto;
import com.hpmath.domain.media.service.ImageService;
import com.hpmath.common.web.authenticationV2.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/media")
public class ImageController {
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "이미지 조회를 위한 API", description = "댓글 및 게시글 조회시 받은 이미지 주소를 포함하여 요청하면 이미지 데이터를 리턴합니다.")
    @GetMapping("/{imageSource}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageSource") String imageSource) throws IOException {
        MediaDto media = imageService.loadImage(imageSource);

        return ResponseEntity.ok()
                .contentType(media.mediaType())
                .body(media.data().readAllBytes());
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Authorization(opened = true)
    public ResponseEntity<ImageUrlDto> postImage(
            @RequestPart(value = "image", required = true) final MultipartFile image
    ) {
        final ImageUrlDto imageUrlDto = imageService.saveSingleImage(image);
        return ResponseEntity.ok(imageUrlDto);
    }
}

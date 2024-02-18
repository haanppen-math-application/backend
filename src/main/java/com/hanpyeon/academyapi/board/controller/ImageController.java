package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.dto.MediaDto;
import com.hanpyeon.academyapi.board.service.ImageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/media")
public class ImageController {
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{imageSource}")
    public ResponseEntity<?> getImage(@PathVariable("imageSource") String imageSource) throws IOException {
        MediaDto media = imageService.loadImage(imageSource);

        return ResponseEntity.ok()
                .contentType(media.mediaType())
                .body(media.data());
    }
}

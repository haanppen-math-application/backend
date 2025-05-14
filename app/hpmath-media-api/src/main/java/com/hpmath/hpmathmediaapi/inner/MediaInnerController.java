package com.hpmath.hpmathmediaapi.inner;

import com.hpmath.hpmathmediadomain.media.dto.MediaInfo;
import com.hpmath.hpmathmediadomain.media.service.MediaQueryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class MediaInnerController {
    private final MediaQueryService mediaQueryService;

    @GetMapping("/api/inner/v1/media/info")
    public ResponseEntity<MediaInfoResponse> getMedia(
            @RequestParam final String mediaSrc
    ) {
        final MediaInfo mediaInfo = mediaQueryService.getMediaInfo(mediaSrc);
        return ResponseEntity.ok(MediaInfoResponse.from(mediaInfo));
    }

    record MediaInfoResponse(
            String mediaName,
            String mediaSrc,
            LocalDateTime createdTime,
            Long runtimeDuration,
            Long fileSize
    ){
        static MediaInfoResponse from(MediaInfo mediaInfo) {
            return new MediaInfoResponse(
                    mediaInfo.mediaName(),
                    mediaInfo.mediaSrc(),
                    mediaInfo.createdTime(),
                    mediaInfo.runtimeDuration(),
                    mediaInfo.fileSize());
        }
    }
}

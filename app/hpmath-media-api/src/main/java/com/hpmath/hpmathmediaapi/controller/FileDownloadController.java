package com.hpmath.hpmathmediaapi.controller;

import com.hpmath.hpmathmediadomain.media.dto.DownloadCommand;
import com.hpmath.hpmathmediadomain.media.dto.DownloadResult;
import com.hpmath.hpmathmediadomain.media.service.DownloadService;
import com.hpmath.hpmathwebcommon.authenticationV2.Authorization;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final DownloadService downloadService;

    @GetMapping("/api/file/download")
    @Authorization(opened = true)
    public ResponseEntity<Resource> downloadFile(
            @RequestParam final String fileSrc
    ) {
        final DownloadResult downloadResult = downloadService.getResource(new DownloadCommand(fileSrc));
        final HttpHeaders headers = setDownloadAttachmentFileName(downloadResult.getFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(downloadResult.getInputStream()));
    }

    private HttpHeaders setDownloadAttachmentFileName(final String fileName) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + "filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return headers;
    }
}

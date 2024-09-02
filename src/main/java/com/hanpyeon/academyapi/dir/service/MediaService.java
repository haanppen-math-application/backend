package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.upload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final UploadService uploadService;

    public RequireNextChunk uploadChunk(final UploadMediaDto uploadMediaDto) {
        return uploadService.upload(uploadMediaDto);
    }
}

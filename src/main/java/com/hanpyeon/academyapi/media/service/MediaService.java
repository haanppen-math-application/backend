package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.DeleteMediaCommand;
import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.media.service.delete.DeleteService;
import com.hanpyeon.academyapi.media.service.upload.MediaUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MediaService {
    private final MediaUploadService uploadService;
    private final DeleteService deleteService;

    public ChunkStoreResult uploadChunk(final UploadMediaCommand uploadMediaDto) {
        return uploadService.upload(uploadMediaDto);
    }

    public void deleteMedia(final DeleteMediaCommand deleteMediaDto) {
        deleteService.delete(deleteMediaDto);
    }
}

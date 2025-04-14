package com.hanpyeon.academyapi.media.service.upload.chunk.group;

import com.hanpyeon.academyapi.media.dto.UploadMediaCommand;
import com.hanpyeon.academyapi.dir.service.form.resolver.DirectoryPathFormResolver;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.exception.MediaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkGroupInfoFactory {
    private final ChunkGroupIdManager chunkGroupIndexCounter;
    private final DirectoryPathFormResolver directoryPathFormResolver;

    public ChunkGroupInfo create(final UploadMediaCommand uploadMediaDto) {
        final ChunkGroupInfo chunkGroupInfo = new BasicChunkGroupInfo(chunkGroupIndexCounter,
                uploadMediaDto.getRequestMemberId(),
                uploadMediaDto.getFileName(),
                uploadMediaDto.getTotalChunkCount(),
                getExtension(uploadMediaDto.getExtension()),
                uploadMediaDto.getMediaDuration()
        );
        chunkGroupInfo.init();
        return chunkGroupInfo;
    }

    private String getExtension(final String extension) {
        final int dotLastIndex = extension.lastIndexOf('.');
        if (dotLastIndex == 0) {
            return extension;
        }
        throw new MediaException("확장자 필드에 .을 포함해 주세요 ex) .mp4 ", ErrorCode.MEDIA_STORE_EXCEPTION);
    }
}

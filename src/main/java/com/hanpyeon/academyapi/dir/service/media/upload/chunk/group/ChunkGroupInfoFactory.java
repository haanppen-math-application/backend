package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.form.resolver.DirectoryPathFormResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkGroupInfoFactory {
    private final ChunkGroupIdManager chunkGroupIndexCounter;
    private final DirectoryPathFormResolver directoryPathFormResolver;

    public ChunkGroupInfo create(final UploadMediaDto uploadMediaDto) {
        final String resolvedPath = getPath(uploadMediaDto.getTargetDirectory());
        final ChunkGroupInfo chunkGroupInfo = new ChunkGroupInfoImpl(chunkGroupIndexCounter,
                uploadMediaDto.getRequestMemberId(),
                resolvedPath,
                uploadMediaDto.getFileName(),
                uploadMediaDto.getTotalChunkCount()
        );
        chunkGroupInfo.init();
        return chunkGroupInfo;
    }

    private String getPath(final String requestPath) {
        return directoryPathFormResolver.resolveToAbsolutePath(requestPath);
    }
}

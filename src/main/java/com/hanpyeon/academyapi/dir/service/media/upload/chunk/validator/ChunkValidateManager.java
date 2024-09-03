package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.amazonaws.services.ec2.model.transform.LaunchTemplateElasticInferenceAcceleratorStaxUnmarshaller;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChunkValidateManager {
    private final List<ChunkValidator> chunkValidators;

    public void validate(final ChunkedFile chunkedFile) {
        chunkValidators.stream()
                .forEach(chunkValidator -> chunkValidator.validate(chunkedFile));
    }
}

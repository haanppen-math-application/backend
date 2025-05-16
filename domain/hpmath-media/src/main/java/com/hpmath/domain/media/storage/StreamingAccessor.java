package com.hpmath.domain.media.storage;

import com.hpmath.domain.media.dto.StreamingCommand;
import com.hpmath.domain.media.dto.StreamingResult;

public interface StreamingAccessor {

    StreamingResult getChunk(final StreamingCommand command);
}

package com.hpmath.academyapi.media.storage;

import com.hpmath.academyapi.media.dto.StreamingCommand;
import com.hpmath.academyapi.media.dto.StreamingResult;

public interface StreamingAccessor {

    StreamingResult getChunk(final StreamingCommand command);
}

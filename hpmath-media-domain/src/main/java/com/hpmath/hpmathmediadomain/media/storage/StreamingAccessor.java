package com.hpmath.hpmathmediadomain.media.storage;

import com.hpmath.hpmathmediadomain.media.dto.StreamingCommand;
import com.hpmath.hpmathmediadomain.media.dto.StreamingResult;

public interface StreamingAccessor {

    StreamingResult getChunk(final StreamingCommand command);
}

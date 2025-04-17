package com.hpmath.hpmathcoreapi.media.storage;

import com.hpmath.hpmathcoreapi.media.dto.StreamingCommand;
import com.hpmath.hpmathcoreapi.media.dto.StreamingResult;

public interface StreamingAccessor {

    StreamingResult getChunk(final StreamingCommand command);
}

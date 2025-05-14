package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.util.List;

public record ImageDeleteEventPayload(
        List<String> imageSrcs
)
implements EventPayload {
}

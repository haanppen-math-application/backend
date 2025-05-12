package com.hpmath.event.payload;

import com.hpmath.event.EventPayload;
import java.util.List;

public record ImageDeleteEventPayload(
        List<String> imageSrcs
)
implements EventPayload {
}

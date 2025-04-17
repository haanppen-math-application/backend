package com.hpmath.academyapi.media.service.uploadV2;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class UniqueIdGenerator {
    public String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}

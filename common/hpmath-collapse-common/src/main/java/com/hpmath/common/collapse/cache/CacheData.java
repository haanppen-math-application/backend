package com.hpmath.common.collapse.cache;

import com.hpmath.common.serializer.DataSerializer;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
class CacheData {
    private String data;
    private LocalDateTime logicalExpireTime;

    public static CacheData create(Object data, Duration ttl) {
        return new CacheData(DataSerializer.serialize(data), LocalDateTime.now().plus(ttl));
    }

    public boolean isLogicalExpired() {
        return logicalExpireTime.isBefore(LocalDateTime.now());
    }

    public <T> T parseToData(Class<T> clazz) {
        return DataSerializer.deserialize(data, clazz);
    }
}

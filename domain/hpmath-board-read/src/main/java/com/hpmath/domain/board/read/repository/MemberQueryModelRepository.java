package com.hpmath.domain.board.read.repository;

import com.hpmath.common.serializer.DataSerializer;
import com.hpmath.domain.board.read.model.MemberQueryModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberQueryModelRepository {
    private final StringRedisTemplate redisTemplate;

    // board::read::member::{memberId}
    private static final String KEY_FORMAT = "board::read::member::%s";

    public void update(final MemberQueryModel memberQueryModel) {
        redisTemplate.opsForValue().set(
                getKey(memberQueryModel.getMemberId()),
                DataSerializer.serialize(memberQueryModel));
    }

    public Optional<MemberQueryModel> get(final Long memberId) {
        final String value = redisTemplate.opsForValue().get(getKey(memberId));
        return value == null ? Optional.empty() : Optional.of(DataSerializer.deserialize(value, MemberQueryModel.class));
    }

    private String getKey(final Long memberId) {
        return String.format(KEY_FORMAT, memberId);
    }
}

package com.hpmath.domain.board.read.repository;

import com.hpmath.common.serializer.DataSerializer;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionQueryModelRepository {
    private final StringRedisTemplate stringRedisTemplate;

    // board-read::question::{questionId}
    private static final String KEY_FORMAT = "board-read::question::%s";

    public void update(final QuestionQueryModel model, @NotNull final Duration ttl) {
        stringRedisTemplate.opsForValue()
                .set(getKey(model.getQuestionId()), DataSerializer.serialize(model), ttl);
    }

    public void delete(final Long questionId) {
        stringRedisTemplate.delete(getKey(questionId));
    }

    public Optional<QuestionQueryModel> get(final Long questionId) {
        final String model = stringRedisTemplate.opsForValue()
                .get(getKey(questionId));

        return model == null ? Optional.empty() : Optional.of(DataSerializer.deserialize(model, QuestionQueryModel.class));
    }

    private String getKey(final Long questionId) {
        return KEY_FORMAT.formatted(questionId);
    }
}

package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentDeletedEventPayload;
import com.hpmath.domain.board.read.model.CommentQueryModel;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
        properties = {"logging.level.com.hpmath.app.consumer=debug"}
)
@Testcontainers
class BoardReadCommentDeletedEventHandlerTest {
    @Autowired
    private QuestionQueryModelRepository questionQueryModelRepository;
    @Autowired
    private BoardReadCommentDeletedEventHandler commentDeletedEventHandler;

    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
        redisContainer.start();
    }


    @Test
    void 댓글_삭제시_쿼리모델_업데이트() {
        final Long questionId = 1L;
        final Long targetCommentId = 5L;
        final QuestionQueryModel questionQueryModel = createQueyModel(questionId, targetCommentId);

        questionQueryModelRepository.update(questionQueryModel, null);

        commentDeletedEventHandler.handle(Event.of(12L, EventType.COMMENT_DELETED_EVENT, new CommentDeletedEventPayload(
                questionId,
                0L,
                targetCommentId,
                1L,
                Collections.emptyList())));

        Assertions.assertThat(questionQueryModelRepository.get(questionId).get().comments().size()).isEqualTo(2);
    }

    @Test
    void 캐시되지_않은_댓글_삭제_무시() {
        final Long questionId = 1L;
        final Long targetCommentId = 5L;
        final QuestionQueryModel questionQueryModel = createQueyModel(questionId, targetCommentId);

        questionQueryModelRepository.update(questionQueryModel, null);

        commentDeletedEventHandler.handle(Event.of(12L, EventType.COMMENT_DELETED_EVENT, new CommentDeletedEventPayload(
                // not exist in cache
                102392L,
                0L,
                targetCommentId,
                1L,
                Collections.emptyList())));

        Assertions.assertThat(questionQueryModelRepository.get(questionId).get().comments().size()).isEqualTo(3);
    }

    private static QuestionQueryModel createQueyModel(Long questionId, Long targetCommentId) {
        return new QuestionQueryModel(
                questionId,
                "test",
                "content",
                LocalDateTime.of(1111, 11, 21, 10, 30),
                List.of("test"),
                false,
                List.of(new CommentQueryModel(
                        targetCommentId,
                        "content",
                        false,
                        List.of("test"),
                        LocalDateTime.of(1111, 11, 21, 10, 30), 2L),
                new CommentQueryModel(
                        1000L,
                        "content",
                        false,
                        List.of("test"),
                        LocalDateTime.of(1111, 11, 21, 10, 30), 2L),
                new CommentQueryModel(
                        1001L,
                        "content",
                        false,
                        List.of("test"),
                        LocalDateTime.of(1111, 11, 21, 10, 30), 2L)),
                1L,
                2L);
    }
}
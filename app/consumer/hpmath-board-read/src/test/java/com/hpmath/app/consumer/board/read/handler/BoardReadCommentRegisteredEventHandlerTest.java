package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.domain.board.read.QuestionQueryModelManager;
import com.hpmath.domain.board.read.model.CommentQueryModel;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
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
class BoardReadCommentRegisteredEventHandlerTest {
    @Autowired
    private QuestionQueryModelManager questionQueryModelManager;
    @Autowired
    private QuestionQueryModelRepository questionQueryModelRepository;
    @Autowired
    private BoardReadCommentRegisteredEventHandler boardReadCommentRegisteredEventHandler;

    static GenericContainer redisContainer = new GenericContainer("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
        redisContainer.start();
    }

    @Test
    void 질문_캐시됐을때_댓글_등록() {
        final Long questionId = 1L;
        final Long targetCommentId = 10L;
        final QuestionQueryModel questionQueryModel = createQueyModel(questionId, 100L);

        questionQueryModelManager.add(questionQueryModel);

        boardReadCommentRegisteredEventHandler.handle(Event.of(12L, EventType.COMMENT_REGISTERED_EVENT, new CommentRegisteredEventPayLoad(
                questionId,
                0L,
                targetCommentId,
                "content",
                1L,
                LocalDateTime.now(),
                Collections.emptyList())));

        Assertions.assertThat(questionQueryModelRepository.get(questionId).get().comments().size()).isEqualTo(4);
    }

    @Test
    void 질문캐시_안됐을떄_댓글등록() {
        final Long questionId = 1L;
        final Long targetCommentId = 10L;

        boardReadCommentRegisteredEventHandler.handle(Event.of(12L, EventType.COMMENT_REGISTERED_EVENT, new CommentRegisteredEventPayLoad(
                questionId,
                0L,
                targetCommentId,
                "content",
                1L,
                LocalDateTime.now(),
                Collections.emptyList())));

        Assertions.assertThat(questionQueryModelRepository.get(questionId).isEmpty()).isTrue();
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
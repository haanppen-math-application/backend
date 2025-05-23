package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentUpdatedEventPayload;
import com.hpmath.domain.board.read.QuestionQueryModelManager;
import com.hpmath.domain.board.read.model.CommentQueryModel;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
class BoardReadCommentUpdatedEventHandlerTest {
    @Autowired
    private QuestionQueryModelManager questionQueryModelManager;
    @Autowired
    private QuestionQueryModelRepository questionQueryModelRepository;
    @Autowired
    private BoardReadCommentUpdatedEventHandler boardReadCommentUpdatedEventHandler;

    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
        redisContainer.start();
    }

    @BeforeAll
    static void init() {
        redisContainer.start();
    }

    @Test
    void 댓글_수정_이벤트_수신시_수정한다() {
        final Long questionId = 1L;
        final Long targetCommentId = 5L;
        final QuestionQueryModel questionQueryModel = createQueryModel(questionId, targetCommentId);

        final String changedContent = "changedContent";
        final List<String> medias = List.of("newImage1", "newImage2");

        questionQueryModelManager.add(questionQueryModel);

        boardReadCommentUpdatedEventHandler.handle(
                Event.of(12L, EventType.COMMENT_UPDATED_EVENT, new CommentUpdatedEventPayload(
                        questionId,
                        targetCommentId,
                        changedContent,
                        1L,
                        Collections.emptyList(),
                        medias)));
        final CommentQueryModel comment = questionQueryModelRepository.get(questionId).get().getComments().stream()
                .filter(commentQueryModel -> commentQueryModel.getCommentId().equals(targetCommentId)).findAny()
                .get();

        Assertions.assertAll(
                () -> Assertions.assertEquals("changedContent", comment.getContent()),
                () -> Assertions.assertEquals(medias, comment.getImages())
        );
    }

    @Test
    void 캐시되지_않은_댓글_수정_무시() {
        final Long questionId = 1L;
        final Long targetCommentId = 5L;
        final QuestionQueryModel questionQueryModel = createQueryModel(questionId, targetCommentId);

        final String changedContent = "changedContent";
        final List<String> medias = List.of("newImage1", "newImage2");

        questionQueryModelManager.add(questionQueryModel);

        boardReadCommentUpdatedEventHandler.handle(
                Event.of(12L, EventType.COMMENT_UPDATED_EVENT, new CommentUpdatedEventPayload(
                        questionId,
                        10000000L,
                        changedContent,
                        1L,
                        Collections.emptyList(),
                        medias)));
    }

    @Test
    void 캐시되지_않은_질문의_댓글_수정_무시() {
        final Long questionId = 1L;
        final Long targetCommentId = 5L;
        final QuestionQueryModel questionQueryModel = createQueryModel(questionId, targetCommentId);

        final String changedContent = "changedContent";
        final List<String> medias = List.of("newImage1", "newImage2");

        questionQueryModelManager.add(questionQueryModel);

        boardReadCommentUpdatedEventHandler.handle(
                Event.of(12L, EventType.COMMENT_UPDATED_EVENT, new CommentUpdatedEventPayload(
                        3000L,
                        targetCommentId,
                        changedContent,
                        1L,
                        Collections.emptyList(),
                        medias)));
    }

    private static QuestionQueryModel createQueryModel(Long questionId, Long targetCommentId) {
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
package com.hpmath.domain.board.read;

import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import com.hpmath.domain.board.read.repository.RecentQuestionRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class QuestionRecentListManager {
    private final RecentQuestionRepository recentQuestionRepository;
    private final BoardQuestionClient boardQuestionClient;

    private static final Long RECENT_QUESTION_CACHE_SIZE = 1000L;

    @Async("workers")
    public CompletableFuture<List<Long>> getPagedResultSortedByDate(final int pageNumber, final int pageSize) {
        final List<Long> questionIds = getRecentQuestionIds(pageNumber, pageSize);
        return CompletableFuture.completedFuture(questionIds);
    }

    @EventListener(ContextRefreshedEvent.class)
    void initialize() {
        log.info("Initializing recent question list manager");
        final List<Long> questionCount = getRecentQuestionIds(0, RECENT_QUESTION_CACHE_SIZE.intValue());
        log.info("Initializing recent questions count: {}", questionCount);
    }

    private List<Long> getRecentQuestionIds(final int pageNumber, final int pageSize) {
        List<Long> questionIds = recentQuestionRepository.getRange(pageNumber * pageSize, pageSize);
        if (questionIds.size() != pageSize) {
            questionIds = boardQuestionClient.getQuestionsSortByDate(pageNumber, pageSize).stream()
                    .peek(question -> recentQuestionRepository.add(question.questionId(), question.registeredDateTime(),
                            RECENT_QUESTION_CACHE_SIZE))
                    .map(QuestionDetailInfo::questionId)
                    .toList();
        }
        return questionIds;
    }
}

package com.hpmath.app.api.board.controller.inner;

import com.hpmath.domain.board.dto.QuestionInfoResult;
import com.hpmath.domain.board.service.question.QuestionQueryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionInnerController {
    private final QuestionQueryService questionQueryService;

    @GetMapping("/api/inner/v1/questions/paged/date-desc")
    public ResponseEntity<List<QuestionInfoResponse>> getPagedQuestions(
            @RequestParam Long pageSize,
            @RequestParam Long pageNumber
    ) {
        final List<QuestionInfoResponse> responses = questionQueryService.loadQuestionsSortByDate(pageSize, pageNumber).stream()
                .map(QuestionInfoResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/api/inner/v1/questions/detail")
    public ResponseEntity<QuestionInfoResponse> getSingleDetail(
            @RequestParam final Long questionId
    ) {
        return ResponseEntity.ok(
                QuestionInfoResponse.from(questionQueryService.getSingleDetail(questionId)));
    }

    @GetMapping("/api/inner/v1/questions/count")
    public ResponseEntity<Long> getQuestionsCount() {
        return ResponseEntity.ok(questionQueryService.getCount());
    }

    record QuestionInfoResponse(
            Long questionId,
            String title,
            String content,
            Boolean solved,
            LocalDateTime registeredDateTime,
            Long ownerId,
            Long targetId,
            List<String> mediaSrcs
    ) {
        public static QuestionInfoResponse from(final QuestionInfoResult questionInfoResult) {
            return new QuestionInfoResponse(
                    questionInfoResult.questionId(),
                    questionInfoResult.title(),
                    questionInfoResult.content(),
                    questionInfoResult.solved(),
                    questionInfoResult.registeredDateTime(),
                    questionInfoResult.ownerId(),
                    questionInfoResult.targetId(),
                    questionInfoResult.mediaSrcs()
            );
        }
    }
}

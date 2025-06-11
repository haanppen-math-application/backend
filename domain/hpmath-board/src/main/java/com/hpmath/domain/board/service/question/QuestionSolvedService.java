package com.hpmath.domain.board.service.question;

import com.hpmath.domain.board.dao.QuestionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class QuestionSolvedService {
    private final QuestionRepository questionRepository;

    @Transactional
    public void solve(@NotNull final Long questionId) {
        questionRepository.findById(questionId)
                .ifPresent(question -> question.setSolved(true));
    }
}

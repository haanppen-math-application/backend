package com.hpmath.domain.board.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.hpmath.domain.board.entity.Question;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @Test
    void findByQuestionId() {
        questionRepository.save(Question.of(List.of("test1", "me1"), "title1", "content", 1L, 2L));
        questionRepository.save(Question.of(List.of("test2", "me2"), "title2", "content", 1L, 2L));
        questionRepository.save(Question.of(List.of("test3", "me3"), "title3", "content", 1L, 2L));
        questionRepository.save(Question.of(List.of("test4", "me4"), "title4", "content", 1L, 2L));
        questionRepository.save(Question.of(List.of("test5", "me5"), "title5", "content", 1L, 2L));
        questionRepository.save(Question.of(List.of("test6", "me6", "plusData"), "title6", "content", 1L, 2L));

        Assertions.assertThat(questionRepository.findQuestionsSortByDate(6L, 0L)).hasSize(13);
    }
}
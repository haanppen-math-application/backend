package com.hpmath.domain.directory.entity;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.hpmath.domain.directory.dao.QuestionImageRepository;
import com.hpmath.domain.directory.dao.QuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionImageTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionImageRepository questionImageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 함께_저장_테스트() {
        final List<String> imageSrcs = List.of("test", "me");
        Question question = Question.of(imageSrcs, "test", "content", null, null);

        final Long id = questionRepository.save(question).getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThat(questionRepository.findById(id).get().getImages()).hasSize(2);
    }

    @Test
    void 댓글_삭제시_soft_delete() {
        final List<String> imageSrcs = List.of("test", "me");
        Question question = Question.of(imageSrcs, "test", "content", null, null);

        final Long id = questionRepository.save(question).getId();

        entityManager.flush();
        entityManager.clear();


        questionRepository.deleteById(id);

        entityManager.flush();
        entityManager.clear();

        assertAll(
                () -> Assertions.assertThat(questionImageRepository.findAll().size()).isEqualTo(2),
                () -> Assertions.assertThat(questionImageRepository.findAll().stream()
                        .filter(image -> image.getQuestion() == null)).hasSize(2)
        );
    }

    @Test
    void 수정_시_동작_테스트() {
        final List<String> imageSrcs = List.of("test", "me");
        Question question = Question.of(imageSrcs, "test", "content", null, null);

        final Long id = questionRepository.save(question).getId();

        entityManager.flush();
        entityManager.clear();

        question = questionRepository.findById(id).get();
        question.changeImages(List.of("new"));

        entityManager.flush();
        entityManager.clear();

        assertAll(
                () -> Assertions.assertThat(questionRepository.findById(id).get().getImages().size()).isEqualTo(1),
                () -> Assertions.assertThat(questionImageRepository.findAll().stream()
                        .filter(image -> image.getQuestion() == null)).hasSize(2)
        );
    }
}

package com.hpmath.domain.board.comment.repository;

import com.hpmath.domain.board.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void countComments() {
        commentRepository.saveAll(
                List.of(
                        Comment.of(1L, 2L, "tset", Collections.emptyList()),
                        Comment.of(1L, 2L, "tset", Collections.emptyList()),
                        Comment.of(1L, 2L, "tset", Collections.emptyList()),
                        Comment.of(2L, 2L, "tset", Collections.emptyList()),
                        Comment.of(2L, 2L, "tset", Collections.emptyList()),
                        Comment.of(2L, 2L, "tset", Collections.emptyList())
                )
        );

        entityManager.flush();
        entityManager.clear();

        Long count = this.commentRepository.countByQuestionId(1L);

        Assertions.assertThat(count).isEqualTo(3);
    }

    @Test
    void imageLoadTestInFinalField() {
        final Long commentId = commentRepository.save(Comment.of(1L, 2L, "tset", List.of("tewst", "testee"))).getId();

        entityManager.flush();
        entityManager.clear();

        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        Assertions.assertThat(comment.getImages()).hasSize(2);
    }
}
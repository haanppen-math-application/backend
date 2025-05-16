package com.hpmath.domain.directory.entity;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.hpmath.domain.board.dao.CommentImageRepository;
import com.hpmath.domain.board.dao.CommentRepository;
import com.hpmath.domain.board.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentImageTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentImageRepository commentImageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 함께_저장_테스트() {
        final List<String> imageSrcs = List.of("test", "me");
        Comment comment = Comment.createComment("test", null, null, imageSrcs);

        final Long id = commentRepository.save(comment).getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThat(commentRepository.findById(id).get().getImages()).hasSize(2);
    }

    @Test
    void 댓글_삭제시_soft_delete() {
        final List<String> imageSrcs = List.of("test", "me");
        Comment comment = Comment.createComment("test", null, null, imageSrcs);

        final Long id = commentRepository.save(comment).getId();

        entityManager.flush();
        entityManager.clear();

        commentRepository.deleteById(id);

        entityManager.flush();
        entityManager.clear();

        assertAll(
                () -> Assertions.assertThat(commentImageRepository.findAll().size()).isEqualTo(2),
                () -> Assertions.assertThat(commentImageRepository.findAll().stream()
                        .filter(image -> image.getComment() == null)).hasSize(2)
        );
    }

    @Test
    void 수정_시_동작_테스트() {
        final List<String> imageSrcs = List.of("test", "me");
        Comment comment = Comment.createComment("test", null, null, imageSrcs);

        final Long id = commentRepository.save(comment).getId();

        entityManager.flush();
        entityManager.clear();

        comment = commentRepository.findById(id).get();
        comment.changeImagesTo(List.of("new"));

        entityManager.flush();
        entityManager.clear();

        assertAll(
                () -> Assertions.assertThat(commentRepository.findById(id).get().getImages().size()).isEqualTo(1),
                () -> Assertions.assertThat(commentImageRepository.findAll().stream()
                        .filter(image -> image.getComment() == null)).hasSize(2)
        );
    }
}
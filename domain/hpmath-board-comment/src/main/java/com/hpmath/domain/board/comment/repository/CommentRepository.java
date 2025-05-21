package com.hpmath.domain.board.comment.repository;

import com.hpmath.domain.board.comment.entity.Comment;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("UPDATE Comment c SET c.ownerId = null WHERE c.ownerId In :ownerIds")
    void updateOwnerToNullIdsIn(@Param("ownerIds") Collection<Long> ownerMemberIds);

    @Query("SELECT COUNT(*) FROM Comment c WHERE c.questionId = :questionId")
    Long countByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.images WHERE c.questionId = :questionId")
    List<Comment> queryCommentsWithMedias(@Param("questionId") Long questionId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.images WHERE c.id = :commentId")
    Optional<Comment> querySingleCommentById(@Param("commentId") Long commentId);
}

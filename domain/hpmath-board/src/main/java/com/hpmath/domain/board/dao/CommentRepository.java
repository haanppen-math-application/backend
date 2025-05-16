package com.hpmath.domain.board.dao;

import com.hpmath.domain.board.entity.Comment;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("UPDATE Comment c SET c.registeredMember = null WHERE c.registeredMember In :ownerIds")
    void updateOwnerToNullIdsIn(@Param("ownerIds") Collection<Long> ownerMemberIds);
}

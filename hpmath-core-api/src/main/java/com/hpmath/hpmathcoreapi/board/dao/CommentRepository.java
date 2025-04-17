package com.hpmath.hpmathcoreapi.board.dao;

import com.hpmath.hpmathcoreapi.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}

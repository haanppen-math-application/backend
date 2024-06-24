package com.hanpyeon.academyapi.board.dao;

import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Slice<Question> findBy(Pageable pageable);
    List<Question> findQuestionsByIdIsGreaterThanEqual(final Long id, final Pageable pageable);
}

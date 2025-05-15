package com.hpmath.domain.directory.dao;

import com.hpmath.domain.directory.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findQuestionsByOwnerMemberId(final Long memberId, final Pageable pageable);
    Page<Question> findQuestionsByOwnerMemberIdAndTitleContaining(final Long memberId, final String title, final Pageable pageable);
    Page<Question> findAllByTitleContaining(final String title, final Pageable pageable);
    Page<Question> findAllBy(final Pageable pageable);
}

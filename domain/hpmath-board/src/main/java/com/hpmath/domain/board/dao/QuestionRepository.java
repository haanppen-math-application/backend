package com.hpmath.domain.board.dao;

import com.hpmath.domain.board.entity.Question;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findQuestionsByOwnerMemberId(final Long memberId, final Pageable pageable);
    Page<Question> findQuestionsByOwnerMemberIdAndTitleContaining(final Long memberId, final String title, final Pageable pageable);
    Page<Question> findAllByTitleContaining(final String title, final Pageable pageable);
    Page<Question> findAllBy(final Pageable pageable);

    @Modifying
    @Query("UPDATE Question q SET q.ownerMemberId = null WHERE q.ownerMemberId In :ownerIds")
    void updateToNullOwnerMemberInfos(@Param("ownerIds") Collection<Long> ownerMemberIds);

    @Modifying
    @Query("UPDATE Question q SET q.targetMemberId = null WHERE q.ownerMemberId In :targetIds")
    void updateToNullTargetMemberInfos(@Param("targetIds") Collection<Long> targetIds);
}

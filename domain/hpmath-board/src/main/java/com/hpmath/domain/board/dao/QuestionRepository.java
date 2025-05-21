package com.hpmath.domain.board.dao;

import com.hpmath.domain.board.dto.QuestionInfo;
import com.hpmath.domain.board.entity.Question;
import java.util.Collection;
import java.util.List;
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

    @Query(
nativeQuery = true,
value = """
SELECT q.id, q.title, q.content, q.solved, q.registered_date_time, q.owner_member, q.target_member, qi.image_src 
FROM (SELECT q1.id FROM question q1 ORDER BY q1.registered_date_time desc LIMIT :limit OFFSET :offset) l 
    JOIN question q ON l.id = q.id 
    JOIN question_image qi ON q.id = qi.question_id
""")
    List<QuestionInfo> findQuestionsSortByDate(
            @Param("limit") final Long limit,
            @Param("offset") final Long offset
    );
}

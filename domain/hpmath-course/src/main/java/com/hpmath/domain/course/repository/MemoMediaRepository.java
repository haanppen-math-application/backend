package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.MemoMedia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoMediaRepository extends JpaRepository<MemoMedia, Long> {
    void deleteMemoMediaByMemo_Id(final Long memoId);
    List<MemoMedia> findAllByMemo_Id(final Long memoId);
    Optional<MemoMedia> findMemoMediaByIdAndMemo_Id(final Long memoMediaId, final Long memoId);

    @Query("SELECT mm from MemoMedia mm "
            + "JOIN FETCH mm.memo "
            + "JOIN FETCH mm.memoMediaAttachments "
            + "JOIN FETCH mm.memo.course "
            + "WHERE mm.id = :memoMediaId")
    Optional<MemoMedia> findMemoMedia(@Param(":memoMediaId") final Long memoMediaId);
    @Modifying
    @Query("UPDATE MemoMedia mm SET mm.sequence = mm.sequence - 1 WHERE mm.memo.id = :memoId AND mm.sequence > :deletedMemoMediaSequence")
    Integer updateMemoMediaSequenceAfterDeleted(@Param("memoId") Long memoId, @Param("deletedMemoMediaSequence") Integer deletedMemoMediaSequence);
}

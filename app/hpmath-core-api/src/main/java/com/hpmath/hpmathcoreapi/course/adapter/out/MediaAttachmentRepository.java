package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.entity.MemoMediaAttachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaAttachmentRepository extends JpaRepository<MemoMediaAttachment, Long> {
    List<MemoMediaAttachment> findAllByMemoMedia_Id(final Long memoMediaId);

    @Query("SELECT attach.memoMedia.memo.course.teacherId FROM MemoMediaAttachment attach WHERE attach.attachmentId = :attachmentId")
    Optional<Long> findCourseOwnerMemberOnThisMemoAttachment(@Param("attachmentId") final Long attachmentId);
}

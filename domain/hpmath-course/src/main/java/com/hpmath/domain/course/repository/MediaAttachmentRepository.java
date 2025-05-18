package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.MemoMediaAttachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaAttachmentRepository extends JpaRepository<MemoMediaAttachment, Long> {
    @Query("SELECT attach.memoMedia.memo.course.teacherId FROM MemoMediaAttachment attach WHERE attach.attachmentId = :attachmentId")
    Optional<Long> findCourseOwnerMemberOnThisMemoAttachment(@Param("attachmentId") final Long attachmentId);
}

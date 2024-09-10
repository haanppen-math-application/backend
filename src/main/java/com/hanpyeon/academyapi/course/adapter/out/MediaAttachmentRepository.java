package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaAttachmentRepository extends JpaRepository<MemoMediaAttachment, Long> {
    List<MemoMediaAttachment> findAllByMemoMedia_Id(final Long memoMediaId);
    @Query("SELECT attach.memoMedia.memo.course.teacher FROM MemoMediaAttachment attach WHERE attach.attachmentId = :attachmentId")
    Optional<Member> findCourseOwnerMemberOnThisMemoAttachment(@Param("attachmentId") final Long attachmentId);
}

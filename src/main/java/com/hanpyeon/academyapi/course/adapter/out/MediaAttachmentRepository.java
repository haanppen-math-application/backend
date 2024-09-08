package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaAttachmentRepository extends JpaRepository<MemoMediaAttachment, Long> {
    List<MemoMediaAttachment> findAllByMemoMedia_Id(final Long memoMediaId);
}

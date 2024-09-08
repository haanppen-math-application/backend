package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaAttachmentRepository extends JpaRepository<MemoMediaAttachment, Long> {
}

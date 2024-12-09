package com.hanpyeon.academyapi.online.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineVideoAttachmentRepository extends JpaRepository<OnlineVideoAttachment, Long> {
}

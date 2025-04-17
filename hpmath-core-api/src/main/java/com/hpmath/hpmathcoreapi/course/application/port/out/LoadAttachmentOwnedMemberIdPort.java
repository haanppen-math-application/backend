package com.hpmath.hpmathcoreapi.course.application.port.out;

public interface LoadAttachmentOwnedMemberIdPort {
    Long findOwnerId(final Long attachmentId);
}

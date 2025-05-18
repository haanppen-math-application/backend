package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DeleteAttachmentCommand {
    private final Long targetAttachmentId;
    private final Long requestMemberId;
    private final Role role;

    public static DeleteAttachmentCommand of(final Long targetAttachmentId, final Long requestMemberId,
                                             final Role role) {
        return new DeleteAttachmentCommand(targetAttachmentId, requestMemberId, role);
    };
}

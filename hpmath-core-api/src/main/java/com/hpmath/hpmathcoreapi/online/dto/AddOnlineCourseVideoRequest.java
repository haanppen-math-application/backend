package com.hpmath.hpmathcoreapi.online.dto;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineVideoCommand.OnlineVideoCommand;

public record AddOnlineCourseVideoRequest(
        Long onlineCourseId,
        OnlineVideoRequest onlineVideoRequest
) {
    public AddOnlineVideoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
        return new AddOnlineVideoCommand(
                onlineCourseId,
                this.onlineVideoRequest().toCommand(),
                requestMemberId,
                requestMemberRole
        );
    }

    public record OnlineVideoRequest(
            String videoSrc,
            Boolean isPreview
    ) {
        public OnlineVideoCommand toCommand() {
            return new OnlineVideoCommand(videoSrc, isPreview);
        }
    }
}

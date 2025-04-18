package com.hpmath.hpmathmediadomain.media.dto;


public interface StreamingCommand {
    String getFileName();

    Long getStartRangeIndex(final Long resourceTotalLength);

    Long getLastRangeIndex(final Long resourceTotalLength);

    Long requestMemberId();
}

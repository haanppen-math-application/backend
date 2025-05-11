package com.hpmath.hpmathwebcommon.paging;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}

package com.hpmath.common.web.paging;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}

package com.hpmath.common.page;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}

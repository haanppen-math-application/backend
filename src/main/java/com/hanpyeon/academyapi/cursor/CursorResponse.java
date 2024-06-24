package com.hanpyeon.academyapi.cursor;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}

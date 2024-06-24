package com.hanpyeon.academyapi.account.dto;

import java.util.List;

public record CursorResponse<T>(List<T> contents, Long nextCursor) {
}

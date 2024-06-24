package com.hanpyeon.academyapi.account.dto;

import org.springframework.lang.NonNull;

import java.util.List;

public record AccountRemoveRequest(
        @NonNull List<Long> targetIds
) {
}

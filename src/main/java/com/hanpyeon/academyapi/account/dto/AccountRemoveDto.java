package com.hanpyeon.academyapi.account.dto;

import java.util.List;

public record AccountRemoveDto(
        List<Long> targetIds
) {
}

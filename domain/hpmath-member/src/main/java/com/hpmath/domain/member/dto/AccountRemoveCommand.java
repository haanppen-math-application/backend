package com.hpmath.domain.member.dto;

import java.util.List;

public record AccountRemoveCommand(
        List<Long> targetIds
) {
}

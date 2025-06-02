package com.hpmath.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AccountRemoveCommand(
        @NotNull
        List<Long> targetIds
) {
}

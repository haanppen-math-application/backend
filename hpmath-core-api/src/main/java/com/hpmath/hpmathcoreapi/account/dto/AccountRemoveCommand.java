package com.hpmath.hpmathcoreapi.account.dto;

import java.util.List;

public record AccountRemoveCommand(
        List<Long> targetIds
) {
}

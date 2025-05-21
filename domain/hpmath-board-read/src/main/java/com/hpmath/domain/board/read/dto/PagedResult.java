package com.hpmath.domain.board.read.dto;

import com.hpmath.common.page.PagedResponse;
import java.util.List;

public record PagedResult<T>(
        List<T> datas,
        Long totalItemCount,
        int currentPageNumber,
        int pageSize
) {
    public static <V> PagedResult<V> of(List<V> datas, Long totalItemCount, int currentPageNumber, int pageSize) {
        return new PagedResult<V>(datas, totalItemCount, currentPageNumber, pageSize);
    }
}

package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.port.out.DeleteMemoPort;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteMemoAdapter implements DeleteMemoPort {

    private final MemoRepository memoRepository;

    @Override
    public void deleteMemo(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new IllegalArgumentException("memoID 부재");
        }
        memoRepository.deleteById(memoId);
    }
}

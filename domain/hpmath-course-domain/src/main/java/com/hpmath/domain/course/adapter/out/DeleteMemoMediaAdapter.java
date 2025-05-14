package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.DeleteMemoMediaPort;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DeleteMemoMediaAdapter implements DeleteMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteRelatedMedias(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new IllegalArgumentException("memoID 부재");
        }
        memoMediaRepository.deleteMemoMediaByMemo_Id(memoId);
    }
}

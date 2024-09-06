package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.DeleteMemoMediaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class DeleteMemoMediaAdapter implements DeleteMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;

    @Override
    public void deleteRelatedMedias(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new IllegalArgumentException("memoID 부재");
        }
        memoMediaRepository.deleteMemoMediaByMemo_Id(memoId);
    }
}

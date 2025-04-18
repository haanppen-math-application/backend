package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.application.port.out.DeleteSingleMemoMediaPort;
import com.hpmath.hpmathcoreapi.course.entity.MemoMedia;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteSingleMemoMediaAdapter implements DeleteSingleMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;

    @Override
    public void delete(Long memoMediaId) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(memoMediaId)
                        .orElseThrow(() -> new MemoMediaException("찾을 수 없음", ErrorCode.MEMO_MEDIA_DELETE_EXCEPTION));
        targetMemoMedia.setNull();
        memoMediaRepository.deleteById(memoMediaId);
    }
}

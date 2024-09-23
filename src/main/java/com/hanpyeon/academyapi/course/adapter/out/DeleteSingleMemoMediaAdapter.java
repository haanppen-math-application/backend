package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.DeleteSingleMemoMediaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteSingleMemoMediaAdapter implements DeleteSingleMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;

    @Override
    public void delete(Long memoMediaId) {
        memoMediaRepository.deleteById(memoMediaId);
    }
}

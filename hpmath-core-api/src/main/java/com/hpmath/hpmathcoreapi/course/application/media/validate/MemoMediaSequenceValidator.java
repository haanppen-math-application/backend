package com.hpmath.hpmathcoreapi.course.application.media.validate;

import com.hpmath.hpmathcoreapi.course.application.exception.MemoMediaException;
import com.hpmath.hpmathcoreapi.course.domain.MemoMedia;
import com.hpmath.hpmathcoreapi.course.domain.MemoMediaContainer;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemoMediaSequenceValidator implements MemoMediaContainerValidator {

    @Override
    public void validate(MemoMediaContainer container) {
        final List<MemoMedia> memoMedias = container.getMemoMedias();
        for (int requireSequence = 0; requireSequence < memoMedias.size(); requireSequence++) {
            boolean currSequenceFounded = false;
            for (final MemoMedia memoMedia : memoMedias) {
                if (memoMedia.getSequence() == requireSequence) {
                    currSequenceFounded = true;
                }
            }
            if (currSequenceFounded == false) {
                throw new MemoMediaException("해당 시퀀스 부재 : " + requireSequence, ErrorCode.MEMO_MEDIA_SEQUENCE);
            }
        }
    }
}

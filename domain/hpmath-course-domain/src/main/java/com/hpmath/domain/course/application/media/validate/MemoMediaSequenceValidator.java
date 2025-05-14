package com.hpmath.domain.course.application.media.validate;

import com.hpmath.domain.course.application.exception.MemoMediaException;
import com.hpmath.domain.course.domain.MemoMedia;
import com.hpmath.domain.course.domain.MemoMediaContainer;
import com.hpmath.hpmathcore.ErrorCode;
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

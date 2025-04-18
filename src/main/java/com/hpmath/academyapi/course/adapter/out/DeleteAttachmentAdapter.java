package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.exception.MemoMediaException;
import com.hpmath.academyapi.course.application.port.out.DeleteAttachmentPort;
import com.hpmath.academyapi.exception.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAttachmentAdapter implements DeleteAttachmentPort {

    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Override
    public void delete(Long attachmentId) {
        if (Objects.isNull(attachmentId)) {
            throw new MemoMediaException("잘못된 api 호출 : " + attachmentId, ErrorCode.CANNOT_FIND_ATTACHMENT);
        }
        mediaAttachmentRepository.deleteById(attachmentId);
    }
}

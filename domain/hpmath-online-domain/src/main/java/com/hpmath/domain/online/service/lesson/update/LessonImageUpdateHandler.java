package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathmediadomain.media.entity.Image;
import com.hpmath.hpmathmediadomain.media.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonImageUpdateHandler implements LessonUpdateHandler {
    private final ImageRepository imageRepository;

    @Override
    public void update(OnlineCourse onlineCourse, UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (updateOnlineLessonInfoCommand.imageSrc() == null) {
            return;
        }
        final Image newImage = loadImage(updateOnlineLessonInfoCommand.imageSrc());
        onlineCourse.setImage(newImage);
    }

    private Image loadImage(final String imageSrc) {
        return imageRepository.findBySrc(imageSrc)
                .orElseThrow(() -> new BusinessException(imageSrc + " : 찾을 수 없는 사진", ErrorCode.NO_SUCH_MEDIA));
    }
}

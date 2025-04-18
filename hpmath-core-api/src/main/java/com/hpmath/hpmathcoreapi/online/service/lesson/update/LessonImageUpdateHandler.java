package com.hpmath.hpmathcoreapi.online.service.lesson.update;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcoreapi.board.service.comment.CommentService;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.media.entity.Image;
import com.hpmath.hpmathcoreapi.media.repository.ImageRepository;
import com.hpmath.hpmathcoreapi.media.service.ImageService;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dto.UpdateOnlineLessonInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonImageUpdateHandler implements LessonUpdateHandler{
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final CommentService commentService;

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

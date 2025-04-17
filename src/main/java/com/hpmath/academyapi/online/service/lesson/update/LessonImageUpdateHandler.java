package com.hpmath.academyapi.online.service.lesson.update;

import com.hpmath.academyapi.board.service.comment.CommentService;
import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.media.entity.Image;
import com.hpmath.academyapi.media.repository.ImageRepository;
import com.hpmath.academyapi.media.service.ImageService;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
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

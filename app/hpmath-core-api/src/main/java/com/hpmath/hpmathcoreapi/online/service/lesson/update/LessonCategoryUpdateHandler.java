package com.hpmath.hpmathcoreapi.online.service.lesson.update;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCategory;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCategoryRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dto.UpdateOnlineLessonInfoCommand;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LessonCategoryUpdateHandler implements LessonUpdateHandler {
    private final OnlineCategoryRepository onlineCategoryRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (Objects.isNull(updateOnlineLessonInfoCommand.categoryId())) {
            return;
        }
        final OnlineCategory onlineCategory = loadSingleCategory(updateOnlineLessonInfoCommand.categoryId());
        onlineCourse.setOnlineCategory(onlineCategory);
    }

    private OnlineCategory loadSingleCategory(final Long onlineCategoryId) {
        return onlineCategoryRepository.findById(onlineCategoryId)
                .orElseThrow(() -> new BusinessException("일치하는 카테고리를 찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }
}

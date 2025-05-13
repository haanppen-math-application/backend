package com.hpmath.domain.online.service.category;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCategoryRepository;
import com.hpmath.domain.online.dto.OnlineCategoryAddCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class OnlineCategoryAddService {
    private static final Long MAX_LEVEL = 2L;

    private final OnlineCategoryRepository onlineCategoryRepository;

    @Transactional
    public void addOnlineCategory(@Valid final OnlineCategoryAddCommand command) {
        final OnlineCategory newCategory = new OnlineCategory(command.categoryName());
        if (Objects.nonNull(command.beforeLevelId())) {
            final OnlineCategory parentCategory = getParentCategory(command);
            validateMaxLevel(parentCategory);
            newCategory.setParentCategory(parentCategory);
        }
        onlineCategoryRepository.save(newCategory);
    }

    private OnlineCategory getParentCategory(final OnlineCategoryAddCommand command) {
        return onlineCategoryRepository.findById(command.beforeLevelId())
                .orElseThrow(() -> new BusinessException(command.beforeLevelId() + "를 찾을 수 없음", ErrorCode.ONLINE_COURSE_EXCEPTION));
    }

    private void validateMaxLevel(final OnlineCategory parentCategory) {
        if (parentCategory.getLevel().equals(MAX_LEVEL)) {
            throw new BusinessException("최대 카테고리 레벨은 " + MAX_LEVEL, ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}

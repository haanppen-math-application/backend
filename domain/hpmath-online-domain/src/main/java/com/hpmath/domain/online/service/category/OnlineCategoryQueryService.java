package com.hpmath.domain.online.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import com.hpmath.domain.online.dao.OnlineCategoryRepository;
import com.hpmath.domain.online.dto.OnlineCategoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineCategoryQueryService {
    private final OnlineCategoryRepository onlineCategoryRepository;

    public List<OnlineCategoryInfo> queryChildCategories(final Long categoryId) {
        return onlineCategoryRepository.queryChildCategories(categoryId);
    }

    public List<OnlineCategoryInfo> queryRootCategories() {
        return onlineCategoryRepository.queryRootCategories();
    }
}

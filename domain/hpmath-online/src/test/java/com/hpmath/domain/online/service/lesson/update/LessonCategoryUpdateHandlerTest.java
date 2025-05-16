package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCategory;
import com.hpmath.domain.online.dao.OnlineCategoryRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LessonCategoryUpdateHandlerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OnlineCategoryRepository onlineCategoryRepository;

    @Test
    void testOnlineCategoryQuery() {
        final OnlineCategory onlineCategory = new OnlineCategory("test1");
        final Long id = onlineCategoryRepository.save(onlineCategory).getId();

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThat(onlineCategoryRepository.findById(id))
                .isNotNull();
    }
}
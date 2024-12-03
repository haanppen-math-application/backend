package com.hanpyeon.academyapi.online;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class OnlineCategoryRepositoryTest {
    @Autowired
    private OnlineCategoryRepository onlineCategoryRepository;

    @Test
    @Transactional
    void testDepth() {
        final OnlineCategory onlineCategory1 = new OnlineCategory(null, "test");
        onlineCategoryRepository.save(onlineCategory1);
        final OnlineCategory onlineCategory2 = new OnlineCategory(onlineCategory1, "test");
        final OnlineCategory onlineCategory3 = new OnlineCategory(onlineCategory1, "test");
        onlineCategoryRepository.saveAll(List.of(onlineCategory2, onlineCategory3));

        Assertions.assertEquals(onlineCategoryRepository.findAllByParentCategory(onlineCategory1).size(), 2);
        Assertions.assertEquals(onlineCategory1.getChildCategories().size(), 2);
    }
}
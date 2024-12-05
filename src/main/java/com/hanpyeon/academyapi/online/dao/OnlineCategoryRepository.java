package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.online.dao.OnlineCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineCategoryRepository extends JpaRepository<OnlineCategory, Long> {
    List<OnlineCategory> findAllByParentCategory(final OnlineCategory onlineCategory);
}

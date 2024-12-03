package com.hanpyeon.academyapi.online;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OnlineCategoryRepository extends JpaRepository<OnlineCategory, Long> {

    List<OnlineCategory> findAllByParentCategory(final OnlineCategory onlineCategory);
}

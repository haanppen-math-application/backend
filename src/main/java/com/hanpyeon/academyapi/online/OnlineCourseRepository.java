package com.hanpyeon.academyapi.online;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OnlineCourseRepository extends JpaRepository<OnlineCourse, Long> {
}

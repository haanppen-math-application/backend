package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CourseRepository extends JpaRepository<Course, Long> {
}

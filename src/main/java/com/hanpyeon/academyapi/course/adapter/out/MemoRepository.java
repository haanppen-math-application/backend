package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByCourseId(Long courseId, Pageable pageable);
    List<Memo> findAllByCourseIdAndTargetDate(final Long courseId, final LocalDate targetDate);
}

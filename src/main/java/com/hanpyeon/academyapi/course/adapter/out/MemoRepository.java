package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MemoRepository extends JpaRepository<Memo, Long> {
    Slice<Memo> findByCourseId(Long courseId, Pageable pageable);
}

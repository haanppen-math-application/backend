package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.entity.Memo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByCourseId(Long courseId, Pageable pageable);

    List<Memo> findAllByCourseIdAndTargetDate(final Long courseId, final LocalDate targetDate);

    @Query("SELECT m FROM MEMO m JOIN FETCH m.course JOIN FETCH m.course.students mc WHERE mc.studentId = :studentId AND m.targetDate BETWEEN :startDate AND :endDate")
    List<Memo> findAllByMonthAndStudentId(@Param("startDate") final LocalDate startDate, @Param("endDate") final LocalDate endDate, @Param("studentId") final Long studentId);
}

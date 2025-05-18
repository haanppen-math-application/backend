package com.hpmath.domain.course.repository;

import com.hpmath.domain.course.entity.Memo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByCourseId(Long courseId, Pageable pageable);

    Optional<Memo> findAllByCourseIdAndTargetDate(final Long courseId, final LocalDate targetDate);

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.course "
            + "JOIN FETCH m.course.students mc "
            + "WHERE mc.studentId = :studentId AND m.targetDate BETWEEN :startDate AND :endDate")
    List<Memo> findAllByMonthAndStudentId(@Param("startDate") final LocalDate startDate, @Param("endDate") final LocalDate endDate, @Param("studentId") final Long studentId);

    @Query("SELECT m FROM Memo m JOIN FETCH m.course WHERE m.id = :memoId")
    Optional<Memo> findWithCourseByMemoId(@Param("memoId") final Long memoId);

    @Query("SELECT m FROM Memo m WHERE m.course.id = :courseId")
    List<Memo> queryMemosByCourseId(
            @Param("courseId") final Long courseId,
            final Pageable pageable
    );

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.course "
            + "JOIN FETCH m.memoMedias "
            + "WHERE m.id = :memoId")
    Optional<Memo> findWithCourseAndMediasByMemoId(@Param("memoId") final Long memoId);
}

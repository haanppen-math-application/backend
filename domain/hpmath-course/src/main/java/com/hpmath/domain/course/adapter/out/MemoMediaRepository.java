package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.entity.MemoMedia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoMediaRepository extends JpaRepository<MemoMedia, Long> {
    void deleteMemoMediaByMemo_Id(final Long memoId);
    List<MemoMedia> findAllByMemo_Id(final Long memoId);
    Optional<MemoMedia> findMemoMediaByIdAndMemo_Id(final Long memoMediaId, final Long memoId);
}

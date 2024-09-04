package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MemoMediaRepository extends JpaRepository<MemoMedia, Long> {
    List<MemoMedia> deleteMemoMediaByMemo_Id(final Long memoId);
    List<MemoMedia> findAllByMemo_Id(final Long memoId);
}

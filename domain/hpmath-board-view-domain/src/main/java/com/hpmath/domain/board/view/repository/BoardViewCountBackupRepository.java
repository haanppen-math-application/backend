package com.hpmath.domain.board.view.repository;

import com.hpmath.domain.board.view.entity.BoardViewCount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardViewCountBackupRepository extends JpaRepository<BoardViewCount, Long> {

    @Modifying
    @Query("UPDATE BoardViewCount bvc SET bvc.viewCount = :viewCount WHERE bvc.boardId = :boardId AND bvc.viewCount < :viewCount")
    Long updateBoardViewCount(@Param("boardId") final Long boardId, @Param("viewCount") final Long viewCount);

    Optional<BoardViewCount> findByBoardId(Long boardId);
}

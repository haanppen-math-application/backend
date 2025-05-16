package com.hpmath.domain.board.view.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "board_view_count")
public class BoardViewCount {
    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "view_count")
    private Long viewCount;

    public static BoardViewCount of(final Long boardId, final Long viewCount) {
        final BoardViewCount boardViewCount = new BoardViewCount();
        boardViewCount.boardId = boardId;
        boardViewCount.viewCount = viewCount;
        return boardViewCount;
    }
}

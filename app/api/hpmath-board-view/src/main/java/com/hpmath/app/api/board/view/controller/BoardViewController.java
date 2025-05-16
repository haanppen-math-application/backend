package com.hpmath.app.api.board.view.controller;

import com.hpmath.app.api.board.view.controller.Responses.BoardViewCount;
import com.hpmath.domain.board.view.ViewCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inner/v1/board/view-count")
public class BoardViewController {
    private final ViewCountService viewCountService;

    @GetMapping
    public ResponseEntity<BoardViewCount> getBoardViewCount(
            @RequestParam final Long boardId
    ) {
        final Long viewCount = viewCountService.getViewCount(boardId);
        return ResponseEntity.ok(new BoardViewCount(viewCount));
    }

    @PostMapping
    public ResponseEntity<BoardViewCount> addBoardViewCount(
            @RequestParam final Long boardId,
            @RequestParam final Long memberId
    ) {
        final Long viewCount = viewCountService.increaseViewCount(boardId, memberId);
        return ResponseEntity.ok(new BoardViewCount(viewCount));
    }
}

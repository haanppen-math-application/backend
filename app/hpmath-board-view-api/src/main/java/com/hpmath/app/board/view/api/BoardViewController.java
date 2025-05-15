package com.hpmath.app.board.view.api;

import com.hpmath.app.board.view.api.Responses.BoardViewCount;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
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
@RequestMapping("/api/v1/board/view-count")
public class BoardViewController {
    private final ViewCountService viewCountService;

    @GetMapping
    @Authorization(opened = true)
    public ResponseEntity<BoardViewCount> getBoardViewCount(
            @RequestParam final Long boardId
    ) {
        final Long viewCount = viewCountService.getViewCount(boardId);
        return ResponseEntity.ok(new BoardViewCount(viewCount));
    }

    @PostMapping
    @Authorization(opened = true)
    public ResponseEntity<BoardViewCount> addBoardViewCount(
            @RequestParam final Long boardId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final Long viewCount = viewCountService.increaseViewCount(boardId, memberPrincipal.memberId());
        return ResponseEntity.ok(new BoardViewCount(viewCount));
    }
}

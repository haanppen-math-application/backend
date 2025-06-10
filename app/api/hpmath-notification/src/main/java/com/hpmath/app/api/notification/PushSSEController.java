package com.hpmath.app.api.notification;

import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class PushSSEController {
    private final SseService sseService;

    @GetMapping(value = "/subscribe/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Authorization(opened = true)
    public SseEmitter subscribe(
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        return sseService.register(memberPrincipal.memberId());
    }
}

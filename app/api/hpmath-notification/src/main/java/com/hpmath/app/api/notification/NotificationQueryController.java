package com.hpmath.app.api.notification;

import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.notification.NotificationQueryService;
import com.hpmath.domain.notification.dto.NotificationResult;
import com.hpmath.domain.notification.dto.QueryPagedNotificationCommand;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationQueryController {
    private final NotificationQueryService notificationQueryService;

    @GetMapping
    @Authorization(opened = true)
    public ResponseEntity<List<NotificationResult>> getNotifications(
            @RequestParam final LocalDateTime before,
            @RequestParam final Integer pageSize,
            @LoginInfo final MemberPrincipal principal
    ) {
        final List<NotificationResult> results = notificationQueryService.queryWithPaged(new QueryPagedNotificationCommand(
                        principal.memberId(),
                        before,
                        pageSize));
        return ResponseEntity.ok(results);
    }
}

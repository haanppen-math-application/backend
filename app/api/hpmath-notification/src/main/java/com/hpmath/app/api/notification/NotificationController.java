package com.hpmath.app.api.notification;

import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.notification.NotificationService;
import com.hpmath.domain.notification.dto.ReadNotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PutMapping
    @Authorization(opened = true)
    public ResponseEntity<Void> setNotificationToRead(
            @RequestParam final Long notificationId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        notificationService.markAsRead(new ReadNotificationCommand(memberPrincipal.memberId(), notificationId));
        return ResponseEntity.ok().build();
    }
}

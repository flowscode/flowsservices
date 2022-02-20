package com.flowcode.notification;

import com.flowcode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest){
        log.info("New Notification... {}", notificationRequest);
        notificationService.send(notificationRequest);
    }
}

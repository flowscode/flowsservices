package com.flowcode.notification;

import com.flowcode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {


    private NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest){
        notificationRepository.save(Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("Flowcode")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build());
    }

}

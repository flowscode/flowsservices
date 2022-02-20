package com.flowcode.notification;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Notification {
    @Id
    @SequenceGenerator(name = "notification_id_sequence", sequenceName = "notification_id_sequence")
    @GeneratedValue(generator = "notification_id_sequence",strategy = GenerationType.SEQUENCE)
    private Integer notificationId;
    private String message;
    private String sender;
    private LocalDateTime sentAt;
    private String toCustomerEmail;
    private Integer toCustomerId;

}

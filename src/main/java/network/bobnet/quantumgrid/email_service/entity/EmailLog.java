package network.bobnet.quantumgrid.email_service.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import network.bobnet.quantumgrid.email_service.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "email_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailLog {

    @Id
    private String id;

    private String recipient;

    private String subject;

    private String body;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private LocalDateTime sentAt;

    private Long createdBy;
}

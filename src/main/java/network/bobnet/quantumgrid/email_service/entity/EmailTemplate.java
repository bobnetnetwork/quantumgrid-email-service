package network.bobnet.quantumgrid.email_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_templates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailTemplate {

    @Id
    private String id;

    private String name;

    private String subject;

    private String body;
}

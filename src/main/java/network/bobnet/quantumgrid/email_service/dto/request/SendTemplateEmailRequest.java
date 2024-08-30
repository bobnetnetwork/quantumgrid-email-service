package network.bobnet.quantumgrid.email_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SendTemplateEmailRequest {

    private String recipient;
    private String templateId;
    private Long createdBy;
    private Map<String, Object> variables;
}

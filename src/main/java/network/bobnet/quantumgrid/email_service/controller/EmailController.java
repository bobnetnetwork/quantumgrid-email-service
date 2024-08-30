package network.bobnet.quantumgrid.email_service.controller;

import lombok.RequiredArgsConstructor;
import network.bobnet.quantumgrid.email_service.dto.request.SendTemplateEmailRequest;
import network.bobnet.quantumgrid.email_service.entity.EmailLog;
import network.bobnet.quantumgrid.email_service.entity.EmailTemplate;
import network.bobnet.quantumgrid.email_service.repository.EmailLogRepository;
import network.bobnet.quantumgrid.email_service.repository.EmailTemplateRepository;
import network.bobnet.quantumgrid.email_service.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;
    private final EmailLogRepository emailLogRepository;
    private final EmailTemplateRepository emailTemplateRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendTemplateEmail(
            @RequestBody SendTemplateEmailRequest request
    ) {

        emailService.sendTemplateEmail(request.getRecipient(), request.getTemplateId(), request.getVariables(), request.getCreatedBy());
        return ResponseEntity.ok("Template email sent successfully.");
    }

    @GetMapping("/logs")
    public ResponseEntity<List<EmailLog>> getEmailLogs() {
        List<EmailLog> emailLogs = emailLogRepository.findAll();
        return ResponseEntity.ok(emailLogs);
    }

    @PostMapping("/templates")
    public ResponseEntity<EmailTemplate> createTemplate(@RequestBody EmailTemplate template) {
        EmailTemplate createdTemplate = emailService.createEmailTemplate(template);
        return ResponseEntity.ok(createdTemplate);
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<EmailTemplate> getTemplateById(@PathVariable String id) {
        return emailTemplateRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/templates/{id}")
    public ResponseEntity<EmailTemplate> updateTemplate(@PathVariable String id, @RequestBody EmailTemplate template) {
        EmailTemplate updatedTemplate = emailService.updateEmailTemplate(id, template);
        return ResponseEntity.ok(updatedTemplate);
    }

    @DeleteMapping("/templates/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        emailService.deleteEmailTemplate(id);
        return ResponseEntity.ok("Template deleted successfully.");
    }

    @GetMapping("/logs/{recipient}")
    public ResponseEntity<List<EmailLog>> getEmailLogsByRecipient(@PathVariable String recipient) {
        List<EmailLog> emailLogs = emailLogRepository.findByRecipient(recipient);
        return ResponseEntity.ok(emailLogs);
    }
}

package network.bobnet.quantumgrid.email_service.service;

import lombok.AllArgsConstructor;
import network.bobnet.quantumgrid.commons.exceptions.NotFoundException;
import network.bobnet.quantumgrid.email_service.entity.EmailLog;
import network.bobnet.quantumgrid.email_service.entity.EmailTemplate;
import network.bobnet.quantumgrid.email_service.enums.Status;
import network.bobnet.quantumgrid.email_service.exception.EmailSendingException;
import network.bobnet.quantumgrid.email_service.repository.EmailLogRepository;
import network.bobnet.quantumgrid.email_service.repository.EmailTemplateRepository;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    private final EmailTemplateRepository emailTemplateRepository;

    private static final String TEMPLATE_NOT_FOUND = "Email template not found";

    private TemplateEngine templateEngine;

    public void sendTemplateEmail(String recipient, String templateId, Map<String, Object> variables, Long createdBy) {
        Optional<EmailTemplate> templateOptional = emailTemplateRepository.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new NotFoundException(TEMPLATE_NOT_FOUND);
        }

        EmailTemplate template = templateOptional.get();
        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(template.getBody(), context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(recipient);
            helper.setSubject(template.getSubject());
            helper.setText(htmlContent, true);

            mailSender.send(message);

            EmailLog emailLog = EmailLog.builder()
                    .recipient(recipient)
                    .subject(template.getSubject())
                    .body(htmlContent)
                    .status(Status.SENT)
                    .sentAt(LocalDateTime.now())
                    .createdBy(createdBy)
                    .build();

            emailLogRepository.save(emailLog);
        } catch (MessagingException e) {
            EmailLog emailLog = EmailLog.builder()
                    .recipient(recipient)
                    .subject(template.getSubject())
                    .body(htmlContent)
                    .status(Status.FAILED)
                    .sentAt(LocalDateTime.now())
                    .createdBy(createdBy)
                    .build();

            emailLogRepository.save(emailLog);

            throw new EmailSendingException("Failed to send email: " + e.getMessage());
        }
    }

    public EmailTemplate createEmailTemplate(EmailTemplate template) {
        return emailTemplateRepository.save(template);
    }

    public EmailTemplate updateEmailTemplate(String id, EmailTemplate template) {
        if (!emailTemplateRepository.existsById(id)) {
            throw new NotFoundException(TEMPLATE_NOT_FOUND);
        }
        template.setId(id);
        return emailTemplateRepository.save(template);
    }

    public void deleteEmailTemplate(String id) {
        if (!emailTemplateRepository.existsById(id)) {
            throw new NotFoundException(TEMPLATE_NOT_FOUND);
        }
        emailTemplateRepository.deleteById(id);
    }
}

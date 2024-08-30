package network.bobnet.quantumgrid.email_service.config;

import lombok.AllArgsConstructor;
import network.bobnet.quantumgrid.email_service.entity.EmailTemplate;
import network.bobnet.quantumgrid.email_service.repository.EmailTemplateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.context.IContext;

import java.util.Optional;

@AllArgsConstructor
@Configuration
public class ThymeleafConfig {

    private final EmailTemplateRepository emailTemplateRepository;

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(stringTemplateResolver());
        return templateEngine;
    }

    @Bean
    public StringTemplateResolver stringTemplateResolver() {
        return new StringTemplateResolver() {
            protected String computeTemplateContent(IContext context, String template) {
                Optional<EmailTemplate> emailTemplateOptional = emailTemplateRepository.findById(template);
                return emailTemplateOptional.map(EmailTemplate::getBody).orElse(null);
            }
        };
    }
}

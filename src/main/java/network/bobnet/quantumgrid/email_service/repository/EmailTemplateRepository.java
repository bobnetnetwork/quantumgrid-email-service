package network.bobnet.quantumgrid.email_service.repository;

import network.bobnet.quantumgrid.email_service.entity.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {
}

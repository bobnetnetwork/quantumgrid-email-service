package network.bobnet.quantumgrid.email_service.repository;

import network.bobnet.quantumgrid.email_service.entity.EmailLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailLogRepository extends MongoRepository<EmailLog, String> {
    List<EmailLog> findByRecipient(String recipient);
}

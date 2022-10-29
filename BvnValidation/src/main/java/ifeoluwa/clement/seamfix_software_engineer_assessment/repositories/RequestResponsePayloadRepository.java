package ifeoluwa.clement.seamfix_software_engineer_assessment.repositories;

import ifeoluwa.clement.seamfix_software_engineer_assessment.entities.RequestAndResponsePayload;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RequestResponsePayloadRepository extends MongoRepository<RequestAndResponsePayload, UUID> {

}

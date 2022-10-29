package ifeoluwa.clement.seamfix_software_engineer_assessment.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "RequestAndResponsePayload")
@Data
public class RequestAndResponsePayload {

    @Id
    private UUID id;
    private String requestPayload;
    private String message;
    private String code;
    private String bvn;
    private String imageDetail;
    private String basicDetail;
}

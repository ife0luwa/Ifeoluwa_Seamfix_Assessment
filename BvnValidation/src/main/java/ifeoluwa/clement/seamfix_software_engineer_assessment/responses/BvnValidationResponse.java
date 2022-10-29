package ifeoluwa.clement.seamfix_software_engineer_assessment.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BvnValidationResponse {

    private String message;
    private String code;
    private String bvn;
    private String imageDetail;
    private String basicDetail;
}

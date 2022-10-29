package ifeoluwa.clement.seamfix_software_engineer_assessment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BvnValidationRequest {

    @NotBlank(message = "One or more of your request parameters failed validation. Please retry")
    private String bvn;
}

package ifeoluwa.clement.seamfix_software_engineer_assessment.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BvnInfo {

    private long id;

    private String bvn;

    private String imageDetail;

    private String basicDetail;
}

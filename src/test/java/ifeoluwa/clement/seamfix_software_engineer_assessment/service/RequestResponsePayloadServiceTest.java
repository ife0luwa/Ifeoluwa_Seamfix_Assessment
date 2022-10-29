package ifeoluwa.clement.seamfix_software_engineer_assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnValidationRequest;
import ifeoluwa.clement.seamfix_software_engineer_assessment.repositories.RequestResponsePayloadRepository;
import ifeoluwa.clement.seamfix_software_engineer_assessment.responses.BvnValidationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RequestResponsePayloadService.class})
@ExtendWith(SpringExtension.class)
class RequestResponsePayloadServiceTest {
    @MockBean
    private RequestResponsePayloadRepository requestResponsePayloadRepository;

    @Autowired
    private RequestResponsePayloadService requestResponsePayloadService;

    /**
     * Method under test: Request & Response payloads are persisted in NoSQL db
     */
    @Test
    void testSavePayloadAsync() {
        BvnValidationRequest bvnValidationRequest = new BvnValidationRequest();
        bvnValidationRequest.setBvn("11131151819");

        BvnValidationResponse bvnValidationResponse = new BvnValidationResponse();
        bvnValidationResponse.setBasicDetail("YWJjZGVmZw==");
        bvnValidationResponse.setBvn("11131151819");
        bvnValidationResponse.setCode("00");
        bvnValidationResponse.setImageDetail("YWJjZGVmZw==");
        bvnValidationResponse.setMessage("SUCCESS");
        requestResponsePayloadService.savePayloadAsync(bvnValidationRequest, bvnValidationResponse);
        assertEquals("11131151819", bvnValidationRequest.getBvn());
        assertEquals("YWJjZGVmZw==", bvnValidationResponse.getBasicDetail());
        assertEquals("SUCCESS", bvnValidationResponse.getMessage());
        assertEquals("YWJjZGVmZw==", bvnValidationResponse.getImageDetail());
        assertEquals("00", bvnValidationResponse.getCode());
        assertEquals("11131151819", bvnValidationResponse.getBvn());
    }

}


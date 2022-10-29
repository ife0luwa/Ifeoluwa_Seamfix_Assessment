package ifeoluwa.clement.seamfix_software_engineer_assessment.service;

import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnValidationRequest;
import ifeoluwa.clement.seamfix_software_engineer_assessment.entities.RequestAndResponsePayload;
import ifeoluwa.clement.seamfix_software_engineer_assessment.repositories.RequestResponsePayloadRepository;
import ifeoluwa.clement.seamfix_software_engineer_assessment.responses.BvnValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author on 28/10/2022
 * @project
 */

@Service
public class RequestResponsePayloadService {

    private RequestResponsePayloadRepository requestResponsePayloadRepository;

    @Autowired
    public RequestResponsePayloadService(RequestResponsePayloadRepository requestResponsePayloadRepository) {
        this.requestResponsePayloadRepository = requestResponsePayloadRepository;
    }

    public void savePayloadAsync(BvnValidationRequest requestPayload, BvnValidationResponse responsePayload) {
        CompletableFuture.runAsync(() -> {
            RequestAndResponsePayload entry = new RequestAndResponsePayload();
            entry.setId(UUID.randomUUID());
            entry.setRequestPayload(requestPayload.getBvn());
            entry.setMessage(responsePayload.getMessage());
            entry.setCode(responsePayload.getCode());
            entry.setBvn(responsePayload.getBvn());
            entry.setImageDetail(responsePayload.getImageDetail());
            entry.setBasicDetail(responsePayload.getBasicDetail());

            requestResponsePayloadRepository.save(entry);
        });
    }
}

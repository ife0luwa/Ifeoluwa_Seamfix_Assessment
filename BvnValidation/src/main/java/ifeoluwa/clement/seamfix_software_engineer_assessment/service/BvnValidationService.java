package ifeoluwa.clement.seamfix_software_engineer_assessment.service;

import ifeoluwa.clement.seamfix_software_engineer_assessment.application_constants.ResponseCode;
import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnInfo;
import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnValidationRequest;
import ifeoluwa.clement.seamfix_software_engineer_assessment.responses.BvnValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ifeoluwa on 28/10/2022
 * @project
 */
@Service
public class BvnValidationService {

    private BvnInfoCacheService bvnInfoCacheService;

    @Autowired
    public BvnValidationService(BvnInfoCacheService bvnInfoCacheService) {
        this.bvnInfoCacheService = bvnInfoCacheService;
    }

    public ResponseEntity<BvnValidationResponse> validateBvn(BvnValidationRequest request) {
        String bvn = request.getBvn();
        if(isBvnEmpty(bvn)) {
            return new ResponseEntity<>(getisBvnEmptyResponse(bvn), HttpStatus.BAD_REQUEST);
        }
        if (!hasValidDigitCount(bvn)) {
            return new ResponseEntity<>(getInvalidBvnDigitLengthResponse(bvn), HttpStatus.BAD_REQUEST);
        }
        if (!containsOnlyDigits(bvn)) {
            return new ResponseEntity<>(getInvalidBvnWithNonDigitsResponse(bvn), HttpStatus.BAD_REQUEST);
        }

        Optional<BvnInfo> optionalBvnDetails = bvnInfoCacheService.findByBvn(bvn);
        if (optionalBvnDetails.isEmpty()) {
            return new ResponseEntity<>(getBvnNotFoundResponse(bvn), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(getValidBvnResponse(optionalBvnDetails.get()), HttpStatus.OK);
    }

    public BvnValidationResponse getResponse(BvnValidationRequest request) {
        String bvn = request.getBvn();
        if(isBvnEmpty(bvn)) {
            return getisBvnEmptyResponse(bvn);
        }
        if (!hasValidDigitCount(bvn)) {
            return getInvalidBvnDigitLengthResponse(bvn);
        }
        if (!containsOnlyDigits(bvn)) {
            return getInvalidBvnWithNonDigitsResponse(bvn);
        }

        Optional<BvnInfo> optionalBvnDetails = bvnInfoCacheService.findByBvn(bvn);
        if (optionalBvnDetails.isEmpty()) {
            return getBvnNotFoundResponse(bvn);
        }

        return getValidBvnResponse(optionalBvnDetails.get());
    }



    public  boolean isBvnEmpty(String bvn) {
        if(bvn.isEmpty() ) {
            return true;
        }
        return false;
    }

    public BvnValidationResponse getisBvnEmptyResponse(String bvn) {
        BvnValidationResponse response = new BvnValidationResponse();
            response.setMessage("One or more of your request parameters failed validation. Please retry");
            response.setCode(ResponseCode.EMPTY);
            response.setBvn(bvn);
            return response;
    }

    public boolean hasValidDigitCount(String bvn) {
        int validDigitCount = 11;
        return bvn.length() == validDigitCount;
    }

    public BvnValidationResponse getInvalidBvnDigitLengthResponse(String bvn) {
        BvnValidationResponse response = new BvnValidationResponse();
        response.setMessage("The searched BVN is invalid");
        response.setCode(ResponseCode.INVALID_BVN_DIGITS);
        response.setBvn(bvn);
        return response;
    }

    public boolean containsOnlyDigits(String bvn) {
        for (int i = 0; i < bvn.length(); i++) {
            char currentChar = bvn.charAt(i);
            boolean isNumber = currentChar >= '0' && currentChar <= '9';
            if (!isNumber)
                return false;
        }
        return true;
    }

    public BvnValidationResponse getInvalidBvnWithNonDigitsResponse(String bvn) {
        BvnValidationResponse response = new BvnValidationResponse();
        response.setMessage("The searched BVN is invalid");
        response.setCode(ResponseCode.BAD_REQUEST);
        response.setBvn(bvn);
        return response;
    }

    public BvnValidationResponse getBvnNotFoundResponse(String bvn) {
        BvnValidationResponse response = new BvnValidationResponse();
        response.setMessage("The searched BVN does not exist");
        response.setCode(ResponseCode.NOT_FOUND);
        response.setBvn(bvn);
        return response;
    }

    public BvnValidationResponse getValidBvnResponse(BvnInfo bvnInfo) {
        BvnValidationResponse response = new BvnValidationResponse();
        response.setMessage("SUCCESS");
        response.setCode(ResponseCode.SUCCESS);
        response.setBvn(bvnInfo.getBvn());
        response.setBasicDetail(bvnInfo.getBasicDetail());
        response.setImageDetail(bvnInfo.getImageDetail());
        return response;
    }
}

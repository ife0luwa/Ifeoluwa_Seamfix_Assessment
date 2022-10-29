package ifeoluwa.clement.seamfix_software_engineer_assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnInfo;
import ifeoluwa.clement.seamfix_software_engineer_assessment.dto.BvnValidationRequest;
import ifeoluwa.clement.seamfix_software_engineer_assessment.responses.BvnValidationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BvnValidationService.class})
@ExtendWith(SpringExtension.class)
class BvnValidationServiceTest {
    @MockBean
    private BvnInfoCacheService bvnInfoCacheService;

    @Autowired
    private BvnValidationService bvnValidationService;


    /**
     * Method under test: Empty BVN in request payload
     */
    @Test
    void testIsBvnEmpty() {
        assertFalse(bvnValidationService.isBvnEmpty("Bvn"));
        assertTrue(bvnValidationService.isBvnEmpty(""));
    }

    @Test
    void testGetisBvnEmptyResponse() {
        BvnValidationResponse actualGetisBvnEmptyResponseResult = bvnValidationService.getisBvnEmptyResponse("Bvn");
        assertEquals("One or more of your request parameters failed validation. Please retry",
                actualGetisBvnEmptyResponseResult.getMessage());
        assertEquals("400", actualGetisBvnEmptyResponseResult.getCode());
        assertEquals("Bvn", actualGetisBvnEmptyResponseResult.getBvn());
    }

    /**
     * Method under test: Invalid BVN (Less than 11 BVN digits) in request payload
     */
    @Test
    void testHasValidDigitCount() {
        assertFalse(bvnValidationService.hasValidDigitCount("Bvn"));
        assertTrue(bvnValidationService.hasValidDigitCount("11131151819"));
    }

    @Test
    void testGetInvalidBvnDigitLengthResponse() {
        BvnValidationResponse actualInvalidBvnDigitLengthResponse = bvnValidationService
                .getInvalidBvnDigitLengthResponse("Bvn");
        assertEquals("The searched BVN is invalid", actualInvalidBvnDigitLengthResponse.getMessage());
        assertEquals("02", actualInvalidBvnDigitLengthResponse.getCode());
        assertEquals("Bvn", actualInvalidBvnDigitLengthResponse.getBvn());
    }

    /**
     * Method under test: Invalid BVN (Contains non digits) in request payload
     */

    @Test
    void testContainsOnlyDigits() {
        assertFalse(bvnValidationService.containsOnlyDigits("Bvn"));
        assertTrue(bvnValidationService.containsOnlyDigits("42"));
    }

    @Test
    void testGetInvalidBvnWithNonDigitsResponse() {
        BvnValidationResponse actualInvalidBvnWithNonDigitsResponse = bvnValidationService
                .getInvalidBvnWithNonDigitsResponse("Bvn");
        assertEquals("The searched BVN is invalid", actualInvalidBvnWithNonDigitsResponse.getMessage());
        assertEquals("400", actualInvalidBvnWithNonDigitsResponse.getCode());
        assertEquals("Bvn", actualInvalidBvnWithNonDigitsResponse.getBvn());
    }


    /**
     * Method under test: Invalid BVN in request payload
     */
    @Test
    void testGetBvnNotFoundResponse() {
        BvnValidationResponse actualBvnNotFoundResponse = bvnValidationService.getBvnNotFoundResponse("Bvn");
        assertEquals("The searched BVN does not exist", actualBvnNotFoundResponse.getMessage());
        assertEquals("01", actualBvnNotFoundResponse.getCode());
        assertEquals("Bvn", actualBvnNotFoundResponse.getBvn());
    }

    /**
     * Method under test: Valid BVN in request payload
     */
    @Test
    void testGetValidBvnResponse() {
        BvnInfo bvnInfo = mock(BvnInfo.class);
        when(bvnInfo.getBasicDetail()).thenReturn("Basic Detail");
        when(bvnInfo.getBvn()).thenReturn("Bvn");
        when(bvnInfo.getImageDetail()).thenReturn("Image Detail");
        BvnValidationResponse actualValidBvnResponse = bvnValidationService.getValidBvnResponse(bvnInfo);
        assertEquals("Basic Detail", actualValidBvnResponse.getBasicDetail());
        assertEquals("SUCCESS", actualValidBvnResponse.getMessage());
        assertEquals("Image Detail", actualValidBvnResponse.getImageDetail());
        assertEquals("00", actualValidBvnResponse.getCode());
        assertEquals("Bvn", actualValidBvnResponse.getBvn());
        verify(bvnInfo).getBasicDetail();
        verify(bvnInfo).getBvn();
        verify(bvnInfo).getImageDetail();
    }
}


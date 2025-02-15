package com.microservices.accounts.controller;

import com.microservices.accounts.dto.CustomerAllDetailsDto;
import com.microservices.accounts.dto.ErrorResponseDto;
import com.microservices.accounts.service.CustomerAllDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/customerDetails", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "REST API for Customer Details of Bank",
        description = "REST API of a Bank to FETCH all details of a Customer"
)
public class CustomerAllDetailsController {

    private final CustomerAllDetailsService customerAllDetailsService;

    /**
     * Fetches all details of a customer based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer.
     * @return ResponseEntity containing the customer details.
     */
    @Operation(
            summary = "Fetch Customer's Accounts, Loans and Cards Details REST API",
            description = "REST API to fetch Customer's Accounts, Loans and Cards Details based on a mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetchAllDetails")
    public ResponseEntity<CustomerAllDetailsDto> fetchCustomerAllDetails(
            @RequestHeader ("sombank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be of 10 digits")
            String mobileNumber) {

        log.debug("SomBank-correlation-id found : {}", correlationId);

        log.info("Fetching all the details for mobile number: {}", mobileNumber);
        CustomerAllDetailsDto customerAllDetailsDto = customerAllDetailsService.fetchCustomerAllDetails(mobileNumber, correlationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAllDetailsDto);
    }

}

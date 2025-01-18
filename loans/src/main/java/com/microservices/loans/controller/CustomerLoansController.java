package com.microservices.loans.controller;

import com.microservices.loans.constants.LoansConstants;
import com.microservices.loans.dto.ErrorResponseDto;
import com.microservices.loans.dto.LoansDto;
import com.microservices.loans.dto.ResponseDto;
import com.microservices.loans.service.CustomerLoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Loans Microservice of Bank",
        description = "CRUD REST APIs of a Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
public class CustomerLoansController {

    private CustomerLoansService customerLoansService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside the Bank",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HTTP Status CREATED"
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
    @PostMapping("/createCustomerLoanByMobileNumber")
    public ResponseEntity<ResponseDto> createCustomerLoan(
            @RequestParam @Pattern(regexp="^\\d{10}$", message = "Mobile number must be 10 digits")
            final String mobileNumber) {

        customerLoansService.createCustomerLoanByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
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
    @GetMapping("/fetchLoanDetailsByMobileNumber/{mobileNumber}")
    public ResponseEntity<LoansDto> fetchLoanDetails(
            @PathVariable @Pattern(regexp="^\\d{10}$",message = "Mobile number must be of 10 digits")
            final String mobileNumber) {

        LoansDto loansDto = customerLoansService.fetchLoanDetailsByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK).body(loansDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/updateCustomerCardDetails")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        Boolean isUpdated = customerLoansService.updateCustomerLoanDetails(loansDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @DeleteMapping("/deleteLoanDetailsByMobileNumber")
    public ResponseEntity<ResponseDto> deleteLoanDetails(
            @RequestParam @Pattern(regexp="^\\d{10}$", message = "Mobile number must be 10 digits")
            final String mobileNumber) {

        Boolean isDeleted = customerLoansService.deleteLoanDetailsByMobileNumber(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }
}

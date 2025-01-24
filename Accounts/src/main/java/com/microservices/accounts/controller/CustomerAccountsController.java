package com.microservices.accounts.controller;

import com.microservices.accounts.constants.ResponseConstants;
import com.microservices.accounts.dto.AccountsContactInfoDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.dto.ErrorResponseDto;
import com.microservices.accounts.dto.ResponseDto;
import com.microservices.accounts.service.CustomerAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts Microservice of Bank",
        description = "CRUD REST APIs of a Bank to CREATE, UPDATE, FETCH AND DELETE account details"
)
public class CustomerAccountsController {

    private final CustomerAccountsService customerAccountsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    /**
     * Creates a new customer account.
     *
     * @param customerDto the customer details to create a new account
     * @return ResponseEntity containing the response status and message
     */
    @Operation(
            summary = "Create Customer Account REST API",
            description = "REST API to create new Customer &  Account inside the Bank",
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
    @PostMapping("/createCustomerAccount")
    public ResponseEntity<ResponseDto> createCustomerAccount(
            @Valid @RequestBody CustomerDto customerDto) {

        log.info("Creating a new account for customer: {}", customerDto);
        customerAccountsService.createCustomerAccounts(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ResponseConstants.STATUS_201, ResponseConstants.MESSAGE_201));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetches customer account details based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return ResponseEntity containing the customer account details
     */
    @Operation(
            summary = "Fetch Customer Account Details REST API",
            description = "REST API to fetch Customer & Account details based on a mobile number",
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
    @GetMapping("/fetchAccountDetailsByMobileNumber/{mobileNumber}")
    public ResponseEntity<CustomerDto> getCustomerAccountsDetailsByMobileNumber(
            @PathVariable @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be of 10 digits")
             String mobileNumber) {

        log.info("Fetching account details for mobile number: {}", mobileNumber);
        CustomerDto customerDto = customerAccountsService
                .getCustomerAccountsDetailsByMobileNumber(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Updates the customer account information.
     *
     * @param customerDto the customer details to be updated
     * @return ResponseEntity containing the response status and message
     */
    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number",
            responses = {
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
    @PutMapping("/updateCustomerAccount")
    public ResponseEntity<ResponseDto> updateCustomerAccount(
            @Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = customerAccountsService.updateCustomerAccounts(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(ResponseConstants.STATUS_200, ResponseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ResponseConstants.STATUS_500, ResponseConstants.MESSAGE_500));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Deletes a customer account based on the provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer to be deleted
     * @return ResponseEntity containing the response status and message
     */

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number",
            responses = {
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
    @DeleteMapping("/deleteCustomerAccountByMobileNumber")
    public ResponseEntity<ResponseDto> deleteCustomerAccountByMobileNumber(
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be of 10 digits")
            String mobileNumber) {
        boolean isDeleted = customerAccountsService.deleteCustomerAccountByMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(ResponseConstants.STATUS_200, ResponseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ResponseConstants.STATUS_500, ResponseConstants.MESSAGE_500));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into Accounts Microservice",
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
    @GetMapping("/buildInfo")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into accounts microservice",
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
    @GetMapping("/javaVersion")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues",
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
    @GetMapping("/contactInfo")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}

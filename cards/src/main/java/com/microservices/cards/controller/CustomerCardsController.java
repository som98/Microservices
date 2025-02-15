package com.microservices.cards.controller;

import com.microservices.cards.constants.ResponseConstants;
import com.microservices.cards.dto.CardsContactInfoDto;
import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.dto.ErrorResponseDto;
import com.microservices.cards.dto.ResponseDto;
import com.microservices.cards.service.CustomerCardsService;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST APIs for Cards Microservice of Bank",
        description = "CRUD REST APIs of a Bank to CREATE, UPDATE, FETCH AND DELETE card details"
)
public class CustomerCardsController {

    private final CustomerCardsService customerCardsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside the Bank",
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
    @PostMapping("/createCustomerCardByMobileNumber")
    public ResponseEntity<ResponseDto> createCustomerCardByMobileNumber(
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be of 10 digits")
            String mobileNumber) {
        customerCardsService.createCustomerCardByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        ResponseConstants.STATUS_201, ResponseConstants.MESSAGE_201));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number",
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
    @GetMapping("/fetchCardDetailsByMobileNumber")
    public ResponseEntity<CardsDto> fetchCardDetailsByMobileNumber(
            @RequestHeader ("sombank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
            final String mobileNumber) {

        log.debug("SomBank-correlation-id found : {}", correlationId);

        CardsDto cardsDto = customerCardsService.fetchCardDetailsByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK).body(cardsDto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number",
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
    @PutMapping("/updateCustomerCardDetails")
    public ResponseEntity<ResponseDto> updateCustomerCardDetails(
            @Valid @RequestBody CardsDto cardsDto) {
        Boolean isUpdated = customerCardsService.updateCustomerCardDetails(cardsDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ResponseConstants.STATUS_200,
                            ResponseConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ResponseConstants.STATUS_417,
                            ResponseConstants.MESSAGE_417_UPDATE));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number",
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
    @DeleteMapping("/deleteCardDetailsByMobileNumber")
    public ResponseEntity<ResponseDto> deleteCardDetailsByMobileNumber(
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
            String mobileNumber) {

        Boolean isDeleted = customerCardsService.deleteCardDetailsByMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ResponseConstants.STATUS_200,
                            ResponseConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ResponseConstants.STATUS_417,
                            ResponseConstants.MESSAGE_417_DELETE));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into Cards Microservice",
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
            description = "Get Java versions details that is installed into Cards microservice",
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
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);

    }
}

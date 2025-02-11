package com.microservices.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.accounts.dto.clientsDto.CardsDto;
import com.microservices.accounts.dto.clientsDto.LoansDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "All Details of a Customer",
        description = "Schema to hold Customer, Account, Loans and Cards information"
)
public class CustomerAllDetailsDto {

    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 5, max = 30, message = "Name should be between 5 and 30 characters")
    @Schema(
            description = "Name of the customer", example = "Somshubhra"
    )
    private String name;

    @NotEmpty(message = "Email Address should not be null or empty")
    @Email(message = "Email Address should be a valid")
    @Schema(
            description = "Email address of the customer", example = "som@gmail.com"
    )
    private String email;

    @NotEmpty(message = "Mobile Number should not be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be of 10 digits")
    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    private String mobileNumber;

    @JsonProperty("accounts")
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

    @JsonProperty("loans")
    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;

    @JsonProperty("cards")
    @Schema(
            description = "Cards details of the Customer"
    )
    private CardsDto cardsDto;


}

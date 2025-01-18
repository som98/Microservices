package com.microservices.accounts.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

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
}

package com.microservices.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@Data
@Builder
public class AccountsDto {

    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Schema(
            description = "Account Number of Bank account", example = "1002545189"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account Type should not be null or empty")
    @Schema(
            description = "Account type of Bank account", example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch Address should not be null or empty")
    @Schema(
            description = " Bank branch address", example = "KOLKATA"
    )
    private String branchAddress;
}

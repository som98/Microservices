package com.microservices.accounts.messageFunctions;

import com.microservices.accounts.service.CustomerAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class AccountsFunctions {

    @Bean
    public Consumer<Long> updateCommunication(CustomerAccountsService customerAccountsService) {
        return accountNumber -> {
            log.info("Updating Communication status for account number: {}", accountNumber.toString());
            Boolean isUpdated = customerAccountsService.updateCommunicationStatus(accountNumber);
            log.info("Is the Communication status updated ? : {}", isUpdated);
        };
    }
}

package com.microservices.cards.messageFunctions;

import com.microservices.cards.service.CustomerCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CardsFunctions {

    @Bean
    public Consumer<String> updateCommunication(CustomerCardsService customerCardsService) {
        return cardNumber -> {
            log.info("Updating Communication status for card number: {}", cardNumber);
            Boolean isUpdated = customerCardsService.updateCommunicationStatus(cardNumber);
            log.info("Is the Communication status updated ? : {}", isUpdated);
        };
    }
}

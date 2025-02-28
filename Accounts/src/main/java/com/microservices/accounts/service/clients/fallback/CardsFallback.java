package com.microservices.accounts.service.clients.fallback;

import com.microservices.accounts.dto.clientsDto.CardsDto;
import com.microservices.accounts.service.clients.CardsFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardDetailsByMobileNumber(String correlationId, String mobileNumber) {
        return null;
    }
}

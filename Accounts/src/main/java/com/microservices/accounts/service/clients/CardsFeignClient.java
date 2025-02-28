package com.microservices.accounts.service.clients;

import com.microservices.accounts.dto.clientsDto.CardsDto;
import com.microservices.accounts.service.clients.fallback.CardsFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/cards/fetchCardDetailsByMobileNumber", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardDetailsByMobileNumber(
            @RequestHeader("sombank-correlation-id") String correlationId,
            @RequestParam final String mobileNumber);

}

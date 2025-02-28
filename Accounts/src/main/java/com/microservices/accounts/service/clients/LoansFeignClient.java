package com.microservices.accounts.service.clients;

import com.microservices.accounts.dto.clientsDto.LoansDto;
import com.microservices.accounts.service.clients.fallback.LoansFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value = "/loans/fetchLoanDetailsByMobileNumber/{mobileNumber}", consumes = "application/json")
    ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestHeader("sombank-correlation-id") String correlationId,
            @PathVariable final String mobileNumber);

}

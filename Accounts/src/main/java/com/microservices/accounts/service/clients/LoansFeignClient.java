package com.microservices.accounts.service.clients;

import com.microservices.accounts.dto.clientsDto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/loans/fetchLoanDetailsByMobileNumber/{mobileNumber}", consumes = "application/json")
    ResponseEntity<LoansDto> fetchLoanDetails(@PathVariable final String mobileNumber);

}

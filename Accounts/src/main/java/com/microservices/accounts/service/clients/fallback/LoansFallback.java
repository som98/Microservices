package com.microservices.accounts.service.clients.fallback;

import com.microservices.accounts.dto.clientsDto.LoansDto;
import com.microservices.accounts.service.clients.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}

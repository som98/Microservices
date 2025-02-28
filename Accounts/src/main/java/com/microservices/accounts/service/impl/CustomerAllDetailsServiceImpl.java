package com.microservices.accounts.service.impl;

import com.microservices.accounts.dto.CustomerAllDetailsDto;
import com.microservices.accounts.dto.clientsDto.CardsDto;
import com.microservices.accounts.dto.clientsDto.LoansDto;
import com.microservices.accounts.entity.Accounts;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountsMapper;
import com.microservices.accounts.mapper.CustomerAllDetailsMapper;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.CustomerAllDetailsService;
import com.microservices.accounts.service.clients.CardsFeignClient;
import com.microservices.accounts.service.clients.LoansFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerAllDetailsServiceImpl implements CustomerAllDetailsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * Retrieves customer's all details based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return a CustomerAllDetailsDto object containing the customer's all details
     */
    @Override
    public CustomerAllDetailsDto fetchCustomerAllDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobile number",
                                mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "customerId",
                                customer.getCustomerId().toString()));

        CustomerAllDetailsDto customerAllDetailsDto = CustomerAllDetailsMapper.customerToCustomerAllDetailsDto(customer);
        customerAllDetailsDto.setAccountsDto(AccountsMapper.accountsToAccountsDto(accounts));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetailsByMobileNumber(correlationId, mobileNumber);

        if (loansDtoResponseEntity != null) {
            customerAllDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        if (cardsDtoResponseEntity != null) {
            customerAllDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerAllDetailsDto;
    }

}

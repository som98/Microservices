package com.microservices.accounts.service.impl;

import com.microservices.accounts.constants.CustomerAccountConstants;
import com.microservices.accounts.dto.AccountsDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Accounts;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.CustomerAlreadyExistsException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountsMapper;
import com.microservices.accounts.mapper.CustomerMapper;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.CustomerAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerAccountsServiceImpl implements CustomerAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * This method is used to create a new account for the given customer.
     *
     * @param customerDto the customer details
     */
    @Override
    public void createCustomerAccounts(CustomerDto customerDto) {
        Optional<Customer> existingCustomer =
                customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException
                    ("Customer already exists with given mobile number "
                            + customerDto.getMobileNumber());
        }

        Customer customer = CustomerMapper.customerDtoToCustomer(customerDto);

        Customer newCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(newCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        return Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(CustomerAccountConstants.SAVINGS.name())
                .branchAddress(CustomerAccountConstants.KOLKATA.name())
                .build();
    }

    /**
     * Retrieves customer account details based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return a CustomerDto object containing the customer's account details
     */
    @Override
    public CustomerDto getCustomerAccountsDetailsByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobile number",
                                mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "customerId",
                                customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.customerToCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.accountsToAccountsDto(accounts));

        return customerDto;
    }

    /**
     * Updates the account information for a given customer.
     *
     * @param customerDto the customer details to be updated
     * @return true if the update was successful, false otherwise
     */
    @Override
    public Boolean updateCustomerAccounts(CustomerDto customerDto) {

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (Objects.nonNull(accountsDto)) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Accounts", "accountNumber", accountsDto.getAccountNumber().toString()
                            ));

            customerRepository.findById(accounts.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Customer", "customerId", accounts.getCustomerId().toString()
                    ));

            Accounts updatedAccounts = AccountsMapper.accountsDtoToAccounts(accountsDto);
            updatedAccounts.setCustomerId(accounts.getCustomerId());
            accountsRepository.save(updatedAccounts);

            Customer updatedCustomer = CustomerMapper.customerDtoToCustomer(customerDto);
            updatedCustomer.setCustomerId(accounts.getCustomerId());
            customerRepository.save(updatedCustomer);

            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Deletes the account information for a given customer.
     *
     * @param mobileNumber the mobile number of the customer to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    @Transactional
    public Boolean deleteCustomerAccountByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobile number",
                                mobileNumber)
                );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;

    }

}

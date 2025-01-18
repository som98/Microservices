package com.microservices.accounts.service;

import com.microservices.accounts.dto.CustomerDto;

public interface CustomerAccountsService {

    /**
     * This method is used to create a new account for the given customer.
     *
     * @param customerDto the customer details
     */
    void createCustomerAccounts(CustomerDto customerDto);

    /**
     * Retrieves customer account details based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return a CustomerDto object containing the customer's account details
     */
    CustomerDto getCustomerAccountsDetailsByMobileNumber(String mobileNumber);

    /**
     * Updates the account information for a given customer.
     *
     * @param customerDto the customer details to be updated
     * @return true if the update was successful, false otherwise
     */
    Boolean updateCustomerAccounts(CustomerDto customerDto);


    /**
     * Deletes the account information for a given customer.
     *
     * @param mobileNumber the mobile number of the customer to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    Boolean deleteCustomerAccountByMobileNumber(String mobileNumber);
}

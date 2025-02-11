package com.microservices.accounts.service;

import com.microservices.accounts.dto.CustomerAllDetailsDto;

public interface CustomerAllDetailsService {

    /**
     * Retrieves customer's all details based on a provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return a CustomerAllDetailsDto object containing the customer's all details
     */
    CustomerAllDetailsDto fetchCustomerAllDetails(String mobileNumber);

}

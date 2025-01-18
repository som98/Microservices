package com.microservices.accounts.mapper;

import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto customerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }
    public static Customer customerDtoToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .build();
    }
}

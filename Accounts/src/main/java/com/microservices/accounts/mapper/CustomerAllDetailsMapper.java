package com.microservices.accounts.mapper;

import com.microservices.accounts.dto.CustomerAllDetailsDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Customer;

public class CustomerAllDetailsMapper {

    public static CustomerAllDetailsDto customerToCustomerAllDetailsDto(Customer customer) {
        return CustomerAllDetailsDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }
    public static Customer customerDtoToCustomer(CustomerAllDetailsDto customerAllDetailsDto) {
        return Customer.builder()
                .name(customerAllDetailsDto.getName())
                .email(customerAllDetailsDto.getEmail())
                .mobileNumber(customerAllDetailsDto.getMobileNumber())
                .build();
    }
}

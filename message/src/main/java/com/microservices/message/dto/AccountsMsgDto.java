package com.microservices.message.dto;

public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}

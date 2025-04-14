package com.microservices.cards.dto;

public record CardsMsgDto(String cardNumber, String mobileNumber, Long availableAmount) {
}

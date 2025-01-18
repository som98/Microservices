package com.microservices.cards.mapper;

import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto cardsToCardsDto(Cards cards) {
        return CardsDto.builder()
                .cardNumber(cards.getCardNumber())
                .cardType(cards.getCardType())
                .mobileNumber(cards.getMobileNumber())
                .totalLimit(cards.getTotalLimit())
                .availableAmount(cards.getAvailableAmount())
                .amountUsed(cards.getAmountUsed())
                .build();
    }

    public static Cards cardsDtoToCards(CardsDto cardsDto) {
        return Cards.builder()
                .cardNumber(cardsDto.getCardNumber())
                .cardType(cardsDto.getCardType())
                .mobileNumber(cardsDto.getMobileNumber())
                .totalLimit(cardsDto.getTotalLimit())
                .availableAmount(cardsDto.getAvailableAmount())
                .amountUsed(cardsDto.getAmountUsed())
                .build();
    }
}

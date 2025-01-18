package com.microservices.cards.service.impl;

import com.microservices.cards.constants.ResponseConstants;
import com.microservices.cards.dto.CardsDto;
import com.microservices.cards.entity.Cards;
import com.microservices.cards.exception.CardAlreadyExistsException;
import com.microservices.cards.exception.ResourceNotFoundException;
import com.microservices.cards.mapper.CardsMapper;
import com.microservices.cards.repository.CardsRepository;
import com.microservices.cards.service.CustomerCardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CustomerCardsServiceImpl implements CustomerCardsService {

    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCustomerCardByMobileNumber(String mobileNumber) {
        Optional<Cards> existingCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(existingCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        return Cards.builder()
                .mobileNumber(mobileNumber)
                .cardNumber(Long.toString(randomCardNumber))
                .cardType(ResponseConstants.CREDIT_CARD)
                .totalLimit(ResponseConstants.NEW_CARD_LIMIT)
                .availableAmount(ResponseConstants.NEW_CARD_LIMIT)
                .amountUsed(0L)
                .build();
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCardDetailsByMobileNumber(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.cardsToCardsDto(cards);
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return Boolean indicating if the update of card details is successful or not
     */
    @Override
    public Boolean updateCustomerCardDetails(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        Cards updatedCards = CardsMapper.cardsDtoToCards(cardsDto);
        updatedCards.setCardId(cards.getCardId());
        cardsRepository.save(updatedCards);
        return Boolean.TRUE;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return Boolean indicating if the delete of card details is successful or not
     */
    @Override
    public Boolean deleteCardDetailsByMobileNumber(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return Boolean.TRUE;
    }

}

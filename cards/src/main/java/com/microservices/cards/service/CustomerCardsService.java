package com.microservices.cards.service;

import com.microservices.cards.dto.CardsDto;

public interface CustomerCardsService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createCustomerCardByMobileNumber(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    CardsDto fetchCardDetailsByMobileNumber(String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return Boolean indicating if the update of card details is successful or not
     */
    Boolean updateCustomerCardDetails(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Boolean indicating if the delete of card details is successful or not
     */
    Boolean deleteCardDetailsByMobileNumber(String mobileNumber);

    /**
     * Updates the Communication Status.
     *
     * @param cardNumber the account number of the customer
     * @return true if the update was successful, false otherwise
     */
    Boolean updateCommunicationStatus(String cardNumber);
}

package com.microservices.loans.service;

import com.microservices.loans.dto.LoansDto;

public interface CustomerLoansService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createCustomerLoanByMobileNumber(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    LoansDto fetchLoanDetailsByMobileNumber(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return Boolean indicating if the update of card details is successful or not
     */
    Boolean updateCustomerLoanDetails(LoansDto loansDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Boolean indicating if the delete of loan details is successful or not
     */
    Boolean deleteLoanDetailsByMobileNumber(String mobileNumber);
}

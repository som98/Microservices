package com.microservices.loans.mapper;

import com.microservices.loans.dto.LoansDto;
import com.microservices.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto loansToLoansDto(Loans loans) {
        return LoansDto.builder()
                .loanNumber(loans.getLoanNumber())
                .loanType(loans.getLoanType())
                .mobileNumber(loans.getMobileNumber())
                .totalLoan(loans.getTotalLoan())
                .amountPaid(loans.getAmountPaid())
                .outstandingAmount(loans.getOutstandingAmount())
                .build();
    }

    public static Loans loansDtoToLoans(LoansDto loansDto) {
        return Loans.builder()
                .loanNumber(loansDto.getLoanNumber())
                .loanType(loansDto.getLoanType())
                .mobileNumber(loansDto.getMobileNumber())
                .totalLoan(loansDto.getTotalLoan())
                .amountPaid(loansDto.getAmountPaid())
                .outstandingAmount(loansDto.getOutstandingAmount())
                .build();
    }
}

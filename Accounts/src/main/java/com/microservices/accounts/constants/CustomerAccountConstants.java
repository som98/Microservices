package com.microservices.accounts.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerAccountConstants {

    //Account Type
    SAVINGS("SAVINGS"),
    CURRENT("CURRENT"),
    LOAN("LOAN"),

    //Branch Address
    BANGALORE("BANGALORE"),
    MUMBAI("MUMBAI"),
    DELHI("DELHI"),
    CHENNAI("CHENNAI"),
    KOLKATA("KOLKATA");

    private final String descriptiveName;
}

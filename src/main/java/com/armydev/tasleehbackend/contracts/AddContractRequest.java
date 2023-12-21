package com.armydev.tasleehbackend.contracts;

import java.sql.Date;

/**
 * InnerContract
 */
public record AddContractRequest(
        String contractNo,
        String description,
        double contractValue,
        Company company,
        Currency currency,
        Date contractDate) {
}

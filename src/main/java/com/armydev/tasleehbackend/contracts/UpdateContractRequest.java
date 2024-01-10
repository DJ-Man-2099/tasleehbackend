package com.armydev.tasleehbackend.contracts;

import java.sql.Date;

/**
 * InnerContract
 */
public record UpdateContractRequest(
    String contractNo,
    String description,
    double contractValue,
    String company,
    String currency,
    Date contractDate) {
}

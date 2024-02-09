package com.armydev.tasleehbackend.supplyingsituation;

import com.armydev.tasleehbackend.contracts.Contract;

/**
 * InnerContract
 */
public record AddSupplyingSituationRequest(
        String category,
        Integer totalQuantity,
        Integer arrivedQuantity,
        Integer remainedQuantity,
        Float percentage,
        Contract contract) {
}

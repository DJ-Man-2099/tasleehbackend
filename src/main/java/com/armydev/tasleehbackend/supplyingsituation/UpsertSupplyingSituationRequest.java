package com.armydev.tasleehbackend.supplyingsituation;

import com.armydev.tasleehbackend.contracts.Contract;

/**
 * InnerSupplyingSituation
 */
public record UpsertSupplyingSituationRequest(
        String product,
        Integer totalQuantity,
        Integer arrivedQuantity,
        Integer remainedQuantity,
        Float percentage,
        Contract contract) {
}

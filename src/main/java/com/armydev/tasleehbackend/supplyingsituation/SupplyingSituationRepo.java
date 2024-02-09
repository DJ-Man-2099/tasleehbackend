package com.armydev.tasleehbackend.supplyingsituation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplyingSituationRepo
        extends JpaRepository<SupplyingSituation, Integer>, JpaSpecificationExecutor<SupplyingSituation> {

}

package com.armydev.tasleehbackend.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplyingSituationRepo
    extends JpaRepository<SupplyingSituation, Integer>, JpaSpecificationExecutor<SupplyingSituation> {

}

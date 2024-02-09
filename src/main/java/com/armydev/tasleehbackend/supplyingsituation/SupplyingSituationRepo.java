package com.armydev.tasleehbackend.supplyingsituation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplyingSituationRepo
                extends JpaRepository<SupplyingSituation, Integer>, JpaSpecificationExecutor<SupplyingSituation> {
        Page<SupplyingSituation> findAllByContractId(Integer contractId, Specification<SupplyingSituation> specs,
                        Pageable pageable);
}

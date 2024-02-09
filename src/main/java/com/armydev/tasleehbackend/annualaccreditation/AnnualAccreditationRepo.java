package com.armydev.tasleehbackend.annualaccreditation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.armydev.tasleehbackend.contracts.Contract;

public interface AnnualAccreditationRepo
		extends JpaRepository<AnnualAccreditation, Integer>, JpaSpecificationExecutor<Contract> {

}

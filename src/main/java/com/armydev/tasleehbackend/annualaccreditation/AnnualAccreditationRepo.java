package com.armydev.tasleehbackend.annualaccreditation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnnualAccreditationRepo
		extends JpaRepository<AnnualAccreditation, Integer>, JpaSpecificationExecutor<AnnualAccreditation> {

}

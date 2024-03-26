package com.armydev.tasleehbackend.annualaccreditation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnualAccreditationRepo
		extends JpaRepository<AnnualAccreditation, Integer>, JpaSpecificationExecutor<AnnualAccreditation> {

	@Query(value = "SELECT openingBank FROM annualaccreditions WHERE openingBank LIKE %:pattern% GROUP BY openingBank LIMIT 10", nativeQuery = true)
	List<String> getOpeningBankSuggestions(@Param("pattern") String Pattern);

}

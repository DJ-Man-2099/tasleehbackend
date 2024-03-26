package com.armydev.tasleehbackend.guaranteeletter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuaranteeLetterRepo
		extends JpaRepository<GuaranteeLetter, Integer>, JpaSpecificationExecutor<GuaranteeLetter> {
	@Query(value = "SELECT reportedBank FROM guaranteeletters WHERE reportedBank LIKE %:pattern% GROUP BY reportedBank LIMIT 10", nativeQuery = true)
	List<String> getReportedBankSuggestions(@Param("pattern") String Pattern);
}

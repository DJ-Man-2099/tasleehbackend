package com.armydev.tasleehbackend.contracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepo extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract> {
  @Query(value = "SELECT currency FROM contracts WHERE currency LIKE %:pattern% GROUP BY currency", nativeQuery = true)
  List<String> getCurrencySuggestions(@Param("pattern") String Pattern);

  @Query(value = "SELECT company FROM contracts WHERE company LIKE %:pattern% GROUP BY company", nativeQuery = true)
  List<String> getCompanySuggestions(@Param("pattern") String Pattern);

  @Query(value = "SELECT contractNo FROM contracts WHERE contractNo LIKE %:pattern% GROUP BY contractNo LIMIT 10", nativeQuery = true)
  List<String> getContractNoSuggestions(@Param("pattern") String Pattern);
}

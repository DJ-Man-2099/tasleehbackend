package com.armydev.tasleehbackend.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ErrandsFilesRepo extends JpaRepository<ContractFiles, Integer>, JpaSpecificationExecutor<Contract> {
  ErrandsFiles findByContractIdAndFileName(Integer id, String fileName);
}

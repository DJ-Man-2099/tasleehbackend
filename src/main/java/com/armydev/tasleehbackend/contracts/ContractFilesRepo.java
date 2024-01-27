package com.armydev.tasleehbackend.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractFilesRepo extends JpaRepository<ContractFiles, Integer>, JpaSpecificationExecutor<Contract> {
  ContractFiles findByContractIdAndFileName(Integer id, String fileName);
}

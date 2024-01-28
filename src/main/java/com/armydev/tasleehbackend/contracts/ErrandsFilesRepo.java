package com.armydev.tasleehbackend.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrandsFilesRepo extends JpaRepository<ErrandsFiles, Integer> {
  ErrandsFiles findByContractIdAndFileName(Integer id, String fileName);
}

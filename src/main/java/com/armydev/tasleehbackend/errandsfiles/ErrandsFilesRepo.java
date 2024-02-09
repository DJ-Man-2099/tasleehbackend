package com.armydev.tasleehbackend.errandsfiles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrandsFilesRepo extends JpaRepository<ErrandsFiles, Integer> {
  ErrandsFiles findByContractIdAndFileName(Integer id, String fileName);
}

package com.armydev.tasleehbackend.annualaccreditation.files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnnualAccreditationFilesRepo
    extends JpaRepository<AnnualAccreditationFiles, Integer>, JpaSpecificationExecutor<AnnualAccreditationFiles> {
  AnnualAccreditationFiles findByAnnualAccreditationIdAndFileName(Integer id,
      String fileName);
}

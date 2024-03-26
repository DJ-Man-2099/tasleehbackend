package com.armydev.tasleehbackend.guaranteeletter.files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuaranteeLetterFilesRepo
    extends JpaRepository<GuaranteeLetterFiles, Integer>, JpaSpecificationExecutor<GuaranteeLetterFiles> {
  GuaranteeLetterFiles findByGuaranteeLetterIdAndFileName(Integer id,
      String fileName);
}

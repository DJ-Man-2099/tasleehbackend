package com.armydev.tasleehbackend.guaranteeletter.availability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuaranteeLetterAvailabilityRepo extends JpaRepository<GuaranteeLetterAvailability, Integer> {
	List<GuaranteeLetterAvailability> findByGuaranteeLetterId(Integer id);
}

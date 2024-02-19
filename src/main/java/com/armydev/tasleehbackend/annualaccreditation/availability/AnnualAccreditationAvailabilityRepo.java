package com.armydev.tasleehbackend.annualaccreditation.availability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualAccreditationAvailabilityRepo extends JpaRepository<AnnualAccreditationAvailability, Integer> {
	List<AnnualAccreditationAvailability> findByAnnualAccreditationId(Integer id);
}

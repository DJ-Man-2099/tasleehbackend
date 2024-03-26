package com.armydev.tasleehbackend.annualaccreditation.availability;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@RequestMapping("annualAccreditionAvailability")
@RestController
@AllArgsConstructor
public class AnnualAccreditationAvailabilityController {
	private final AnnualAccreditationAvailabilityRepo repo;

	@GetMapping("{aaId}")
	public ResponseEntity<Map<String, Object>> getHistory(@NonNull @PathVariable("aaId") Integer id) {
		var result = new HashMap<String, Object>();

		var availability = repo.findByAnnualAccreditationId(id);
		result.put("rows", availability);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}
}

package com.armydev.tasleehbackend.suggestions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditationRepo;
import com.armydev.tasleehbackend.contracts.ContractRepo;
import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetterRepo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("suggestions")
@AllArgsConstructor
public class SuggestionsController {
	private final ContractRepo contractRepo;
	private final AnnualAccreditationRepo aaRepo;
	private final GuaranteeLetterRepo glRepo;

	@GetMapping(value = "{type}")
	public ResponseEntity<Map<String, Object>> getSuggestions(@PathVariable("type") String type,
			@RequestParam(value = "pattern", required = false) String pattern) {
		var result = new HashMap<String, Object>();
		if (pattern == null) {
			pattern = "";
		}
		try {
			List<String> data = List.of();
			switch (type) {
				case "company":
					data = contractRepo.getCompanySuggestions(pattern);
					break;
				case "currency":
					data = contractRepo.getCurrencySuggestions(pattern);
					break;
				case "contractNo":
					data = contractRepo.getContractNoSuggestions(pattern);
					break;
				case "openingBank":
					// TODO: Add All Opening Banks
					data = aaRepo.getOpeningBankSuggestions(pattern);
					break;
				case "reportedBank":
					data = glRepo.getReportedBankSuggestions(pattern);
					break;
			}
			result.put(type, data);
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.ok(result);
		}
	}
}

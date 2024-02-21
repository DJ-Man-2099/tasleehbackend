package com.armydev.tasleehbackend.supplyingsituation;

import static com.armydev.tasleehbackend.supplyingsituation.SupplyingSituationSpecs.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.armydev.tasleehbackend.contracts.Contract;
import com.armydev.tasleehbackend.contracts.ContractRepo;
import com.armydev.tasleehbackend.helpers.RequestsHelper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("contractSupplyingSituation")
@AllArgsConstructor
public class SupplyingSituationController {

	private final SupplyingSituationRepo repo;
	private final ContractRepo contractRepo;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllSupplyingSituation(@RequestParam Map<String, String> searchParams)
			throws Exception {
		var result = new HashMap<String, Object>();
		// Get Filters as String Map
		Specification<SupplyingSituation> filter = Specification.where(null);
		var helper = new RequestsHelper<SupplyingSituation>();
		try {
			Specification<SupplyingSituation> finalFilter = helper.getFilters(
					SupplyingSituation.class,
					searchParams,
					specsMap);
			filter = Specification.where(filter)
					.and(finalFilter);
		} catch (Exception e) {
			result.put("status", HttpStatus.BAD_REQUEST.value());
			result.put("error",
					e.getMessage());
			return ResponseEntity.ok(result);
		}
		// Get Results
		var contractsPage = repo.findAll(filter);
		result.put("data", contractsPage);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PostMapping("{contractId}")
	public ResponseEntity<Map<String, Object>> createContractSupplyingSituation(
			@NonNull @PathVariable("contractId") Integer id,
			@RequestBody UpsertSupplyingSituationRequest newSupplyingSituation) {
		var result = new HashMap<String, Object>();
		Contract contract = contractRepo.findById(id).orElseThrow();
		SupplyingSituation supplyingSituation = new SupplyingSituation();
		supplyingSituation.product = newSupplyingSituation.product();
		supplyingSituation.arrivedQuantity = newSupplyingSituation.arrivedQuantity();
		supplyingSituation.remainedQuantity = newSupplyingSituation.remainedQuantity();
		supplyingSituation.totalQuantity = newSupplyingSituation.totalQuantity();
		supplyingSituation.percentage = newSupplyingSituation.percentage();
		supplyingSituation.contract = contract;
		repo.save(supplyingSituation);
		result.put("message", "Supplying Situation Created Successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> updateContractSupplyingSituation(@NonNull @PathVariable("id") Integer id,
			@RequestBody UpsertSupplyingSituationRequest newSupplyingSituation) {
		var result = new HashMap<String, Object>();
		SupplyingSituation supplyingSituation = repo.findById(id).orElseThrow();
		supplyingSituation.product = newSupplyingSituation.product();
		supplyingSituation.arrivedQuantity = newSupplyingSituation.arrivedQuantity();
		supplyingSituation.remainedQuantity = newSupplyingSituation.remainedQuantity();
		supplyingSituation.totalQuantity = newSupplyingSituation.totalQuantity();
		supplyingSituation.percentage = newSupplyingSituation.percentage();
		repo.save(supplyingSituation);
		result.put("message", "Supplying Situation Updated Successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> deleteContractSupplyingSituation(
			@NonNull @PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		SupplyingSituation supplyingSituation;
		try {
			supplyingSituation = repo.findById(id).orElseThrow();
			if (supplyingSituation == null) {
				throw new Exception("Supplying Situation Not Found");
			}
			repo.delete(supplyingSituation);
			result.put("message", "Supplying Situation deleted Successfully");
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);

		} catch (Exception e) {
			result.put("message", "Supplying Situation Not Found");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping("{contractId}")
	public ResponseEntity<Map<String, Object>> getContractSupplyingSituation(
			@NonNull @PathVariable("contractId") Integer contractId, @RequestParam Map<String, String> searchParams)
			throws Exception {
		var result = new HashMap<String, Object>();
		Contract current = contractRepo.findById(contractId).orElseThrow();
		// Get Filters as String Map
		Specification<SupplyingSituation> filter = Specification.where(specsMap.get("contract").apply(current));
		var helper = new RequestsHelper<SupplyingSituation>();
		try {
			Specification<SupplyingSituation> finalFilter = helper.getFilters(
					SupplyingSituation.class,
					searchParams,
					specsMap);
			filter = Specification.where(filter)
					.and(finalFilter);
		} catch (Exception e) {
			result.put("status", HttpStatus.BAD_REQUEST.value());
			result.put("error",
					e.getMessage());
			return ResponseEntity.ok(result);
		}
		// Get Results
		var contractsPage = repo.findAll(filter);
		result.put("data", contractsPage);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

}

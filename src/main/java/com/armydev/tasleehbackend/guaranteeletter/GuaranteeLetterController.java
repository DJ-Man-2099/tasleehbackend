package com.armydev.tasleehbackend.guaranteeletter;

import static com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetterSpecs.*;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.armydev.tasleehbackend.contracts.Contract;
import com.armydev.tasleehbackend.contracts.ContractRepo;
import com.armydev.tasleehbackend.guaranteeletter.availability.Action;
import com.armydev.tasleehbackend.guaranteeletter.availability.GuaranteeLetterAvailability;
import com.armydev.tasleehbackend.guaranteeletter.availability.GuaranteeLetterAvailabilityRepo;
import com.armydev.tasleehbackend.guaranteeletter.files.GuaranteeLetterFiles;
import com.armydev.tasleehbackend.guaranteeletter.files.GuaranteeLetterFilesRepo;
import com.armydev.tasleehbackend.helpers.RequestsHelper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("guaranteeLetters")
@AllArgsConstructor
public class GuaranteeLetterController {

	private final GuaranteeLetterRepo repo;
	private final GuaranteeLetterAvailabilityRepo avaRepo;
	private final GuaranteeLetterFilesRepo filesRepo;
	private final ContractRepo contractRepo;
	private final Path rootLocation = Paths.get("guaranteeLetterUploads");

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllGuaranteeLetters(
			@RequestParam Map<String, String> searchParams)
			throws Exception {
		var result = new HashMap<String, Object>();
		// Set Filters
		Specification<GuaranteeLetter> filters = Specification.where(null);
		var helper = new RequestsHelper<GuaranteeLetter>();

		try {
			filters = Specification.where(filters)
					.and(helper.getFilters(GuaranteeLetter.class, searchParams, specsMap));
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.ok(result);
		}
		// Get Data
		var data = repo.findAll(filters);
		result.put("data", data);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PostMapping("{contractId}")
	public ResponseEntity<Map<String, Object>> createGuaranteeLetter(
			@NonNull @PathVariable("contractId") Integer contractId,
			@RequestBody UpsertGuaranteeLetterRequest current) {
		var result = new HashMap<String, Object>();
		var gl = new GuaranteeLetter();
		Contract contract;
		try {
			contract = contractRepo.findById(contractId).orElseThrow();
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		gl.contract = contract;
		gl.letterSerialNo = current.letterSerialNo();
		gl.guaranteeLetterValue = current.guaranteeLetterValue();
		gl.letterType = current.letterType();
		gl.reportedBank = current.reportedBank();
		gl.latestDate = current.latestDate();
		repo.save(gl);
		var ava = new GuaranteeLetterAvailability();
		ava.guaranteeLetter = gl;
		ava.action = Action.koki;
		ava.action_date = current.latestDate();
		avaRepo.save(ava);
		result.put("message", "new guarantee letter created successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> updateContractGuaranteeLetter(@NonNull @PathVariable("id") Integer id,
			@RequestBody UpsertGuaranteeLetterRequest current) {
		var result = new HashMap<String, Object>();
		GuaranteeLetter gl;
		try {
			gl = repo.findById(id).orElseThrow();
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		gl.letterSerialNo = current.letterSerialNo();
		gl.guaranteeLetterValue = current.guaranteeLetterValue();
		gl.letterType = current.letterType();
		gl.reportedBank = current.reportedBank();
		gl.latestDate = current.latestDate();
		repo.save(gl);
		var ava = new GuaranteeLetterAvailability();
		ava.guaranteeLetter = gl;
		ava.action = Action.koki;
		ava.action_date = current.latestDate();
		avaRepo.save(ava);
		result.put("message", "guarantee letter updated successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> deleteContractGuaranteeLetter(
			@NonNull @PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		GuaranteeLetter aa;
		try {
			aa = repo.findById(id).orElseThrow();
			if (aa != null) {
				repo.delete(aa);
			}
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		result.put("message", "guarantee letter deleted successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@GetMapping("getLetter/{id}")
	public ResponseEntity<Map<String, Object>> getGuarantee(@NonNull @PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		// Get Data
		try {
			var data = repo.findById(id).orElseThrow();
			result.put("data", data);
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("error", "Guarantee Letter doesn't exist");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping("{contractId}")
	public ResponseEntity<Map<String, Object>> getContractGuaranteeLetter(
			@NonNull @PathVariable("contractId") Integer contractId)
			throws Exception {
		var result = new HashMap<String, Object>();
		// Set Filters
		Contract contract;
		try {
			contract = contractRepo.findById(contractId).orElseThrow();
		} catch (Exception e) {
			result.put("error", "Contract doesn't exist");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		Specification<GuaranteeLetter> filters = Specification.where(specsMap.get("contract").apply(contract));
		// Get Data
		var data = repo.findAll(filters);
		result.put("data", data);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "{id}/upload")
	public ResponseEntity<Map<String, Object>> uploadGuaranteeLetterFiles(@NonNull @PathVariable("id") Integer id,
			@RequestParam("guaranteeLetterFiles") List<MultipartFile> files) {
		var result = new HashMap<String, Object>();
		var guaranteeLetter = repo.findById(id).orElseThrow();
		Path destination;
		try {
			for (MultipartFile multipartFile : files) {
				if (!multipartFile.isEmpty()) {
					destination = this.rootLocation
							.resolve(Paths.get(id.toString(),
									URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8")))
							.normalize();
					System.out.println(destination);
					var folder = new File(
							this.rootLocation.resolve(Paths.get(id.toString())).normalize().toString());
					if (!folder.exists()) {
						System.out.println("Folder not found");
						System.out.println(folder.mkdirs());
					}
					try (InputStream inputStream = multipartFile.getInputStream()) {
						Files.copy(inputStream, destination.toAbsolutePath(),
								StandardCopyOption.REPLACE_EXISTING);
						var file = new GuaranteeLetterFiles();
						file.guaranteeLetter = guaranteeLetter;
						file.fileName = URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8");
						file.filePath = destination.toString();
						filesRepo.save(file);
					}
				}
			}
		} catch (Exception e) {
			result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.put("error", e.getMessage());
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/guaranteeLetterFiles/{id}")
	public ResponseEntity<Map<String, Object>> getGuaranteeLetterFiles(@NonNull @PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		var innerResult = new HashMap<String, Object>();
		try {
			var data = repo.findById(id).orElseThrow().files;
			innerResult.put("rows", data);
			result.put("data", innerResult);
			result.put("success", true);
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("error", "Guarantee Letter doesn't exist");
			result.put("success", false);
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
	}
}

package com.armydev.tasleehbackend.annualaccreditation;

import static com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditationSpecs.*;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.armydev.tasleehbackend.contracts.Contract;
import com.armydev.tasleehbackend.contracts.ContractRepo;
import com.armydev.tasleehbackend.helpers.RequestsHelper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/annualAccreditions")
@AllArgsConstructor
public class AnnualAccreditationController {

	private final AnnualAccreditationRepo repo;
	private final AnnualAccreditationFilesRepo filesRepo;
	private final ContractRepo contractRepo;
	private final Path rootLocation = Paths.get("annualAccreditionUploads");

	@GetMapping
	public ResponseEntity<Map<String, Object>> getMethodName(@RequestParam Map<String, String> searchParams)
			throws Exception {
		var result = new HashMap<String, Object>();
		// Set Filters
		Specification<AnnualAccreditation> filters = Specification.where(null);
		var helper = new RequestsHelper<AnnualAccreditation>();

		try {
			filters = Specification.where(filters)
					.and(helper.getFilters(AnnualAccreditation.class, searchParams, specsMap));
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
	public ResponseEntity<Map<String, Object>> createAnnualAccredition(@PathVariable("contractId") Integer contractId,
			@RequestBody UpsertAnnualAccreditationRequest current) {
		var result = new HashMap<String, Object>();
		var aa = new AnnualAccreditation();
		Contract contract;
		try {
			if (contractId == null) {
				result.put("error", "Contract ID is required");
				result.put("status", HttpStatus.BAD_REQUEST.value());
				return ResponseEntity.ok(result);
			}
			contract = contractRepo.findById(contractId).orElseThrow();
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		aa.contract = contract;
		aa.accreditionNo = current.accreditionNo();
		aa.accreditionValue = current.accreditionValue();
		aa.currency = current.currency();
		aa.openingBank = current.openingBank();
		aa.openingDate = current.openingDate();
		aa.expiringDate = current.expiringDate();
		repo.save(aa);
		result.put("message", "new annual accreditation created successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> updateContractAnnualAccredition(@PathVariable("id") Integer id,
			@RequestBody UpsertAnnualAccreditationRequest current) {
		var result = new HashMap<String, Object>();
		AnnualAccreditation aa;
		try {
			if (id == null) {
				result.put("error", "Annual Accreditation ID is required");
				result.put("status", HttpStatus.BAD_REQUEST.value());
				return ResponseEntity.ok(result);
			}
			aa = repo.findById(id).orElseThrow();
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		aa.accreditionNo = current.accreditionNo();
		aa.accreditionValue = current.accreditionValue();
		aa.currency = current.currency();
		aa.openingBank = current.openingBank();
		aa.openingDate = current.openingDate();
		aa.expiringDate = current.expiringDate();
		repo.save(aa);
		result.put("message", "annual accreditation updated successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> deleteContractAnnualAccredition(@PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		AnnualAccreditation aa;
		try {
			if (id == null) {
				result.put("error", "Annual Accreditation ID is required");
				result.put("status", HttpStatus.BAD_REQUEST.value());
				return ResponseEntity.ok(result);
			}
			aa = repo.findById(id).orElseThrow();
			if (aa != null) {
				repo.delete(aa);
			}
		} catch (Exception e) {
			result.put("error", e.getMessage());
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		result.put("message", "annual accreditation deleted successfully");
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getAnnual/{id}")
	public ResponseEntity<Map<String, Object>> getAnnual(@PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		// Get Data
		if (id == null) {
			result.put("error", "Annual Accreditation ID is required");
			result.put("status", HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.ok(result);
		}
		try {
			var data = repo.findById(id).orElseThrow();
			result.put("data", data);
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("error", "Annual Accreditation doesn't exist");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping("{contractId}")
	public ResponseEntity<Map<String, Object>> getContractAnnualAccredition(
			@PathVariable("contractId") Integer contractId)
			throws Exception {
		var result = new HashMap<String, Object>();
		// Set Filters
		if (contractId == null) {
			result.put("error", "Contract ID is required");
			result.put("status", HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.ok(result);

		}
		Contract contract;
		try {
			contract = contractRepo.findById(contractId).orElseThrow();
		} catch (Exception e) {
			result.put("error", "Contract doesn't exist");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
		Specification<AnnualAccreditation> filters = Specification.where(specsMap.get("contract").apply(contract));
		// Get Data
		var data = repo.findAll(filters);
		result.put("data", data);
		result.put("status", HttpStatus.OK.value());
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "{id}/upload")
	public ResponseEntity<Map<String, Object>> uploadAnnualAccreditionFiles(@PathVariable("id") Integer id,
			@RequestParam("annualAcreditionFiles") List<MultipartFile> files) {
		var result = new HashMap<String, Object>();
		if (id == null) {
			throw new IllegalArgumentException("Contract ID cannot be null");
		}
		var annualAccreditation = repo.findById(id).orElseThrow();
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
						var file = new AnnualAccreditationFiles();
						file.annualAccreditation = annualAccreditation;
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

	@GetMapping(value = "/annualAccreditionsFiles/{id}")
	public ResponseEntity<Map<String, Object>> getAnnualAccreditionFiles(@PathVariable("id") Integer id) {
		var result = new HashMap<String, Object>();
		var innerResult = new HashMap<String, Object>();
		try {
			if (id == null) {
				throw new IllegalArgumentException("Annual Accreditation ID cannot be null");
			}
			var data = repo.findById(id).orElseThrow().files;
			innerResult.put("rows", data);
			result.put("data", innerResult);
			result.put("success", true);
			result.put("status", HttpStatus.OK.value());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("error", "Annual Accreditation doesn't exist");
			result.put("success", false);
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok(result);
		}
	}
}

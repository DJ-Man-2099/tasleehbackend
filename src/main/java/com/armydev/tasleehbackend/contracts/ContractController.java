package com.armydev.tasleehbackend.contracts;

import static com.armydev.tasleehbackend.contracts.ContractSpecs.*;

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
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import com.armydev.tasleehbackend.errandsfiles.ErrandsFiles;
import com.armydev.tasleehbackend.errandsfiles.ErrandsFilesRepo;
import com.armydev.tasleehbackend.helpers.RequestsHelper;

import lombok.AllArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/contracts")
@AllArgsConstructor
public class ContractController {

  private final ContractRepo repo;
  private final ContractFilesRepo filesRepo;
  private final ErrandsFilesRepo errandsFilesRepo;
  private final Path rootLocation = Paths.get("uploads");

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllContracts(@RequestParam Map<String, String> searchParams)
      throws Exception {
    var result = new HashMap<String, Object>();
    // Get Desired Page
    int pageSize = searchParams.containsKey("pageSize") ? Integer.parseInt(searchParams.get("pageSize")) : 10;
    int currentPage = searchParams.containsKey("page") ? Integer.parseInt(searchParams.get("page")) : 1;
    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(Direction.DESC, "id"));
    // Set Filters
    Specification<Contract> filter = Specification.where(null);
    var helper = new RequestsHelper<Contract>();
    try {
      Specification<Contract> finalFilter = helper.getFilters(
          Contract.class,
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
    Page<Contract> contractsPage = repo.findAll(filter, pageable);
    result.put("rows", contractsPage.toList());
    result.put("count", contractsPage.getTotalPages());
    result.put("status", HttpStatus.OK.value());
    return ResponseEntity.ok(result);

  }

  @GetMapping(value = "{id:[\\d]++}/")
  public ResponseEntity<Map<String, Object>> getContractById(@NonNull @PathVariable("id") Integer id) {
    var result = new HashMap<String, Object>();
    try {
      var data = repo.findById(id).orElseThrow();
      result.put("data", data);
      result.put("status", HttpStatus.OK.value());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      result.put("error", "Contract doesn't exist");
      result.put("status", HttpStatus.NOT_FOUND.value());
      return ResponseEntity.ok(result);
    }
  }

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
          data = repo.getCompanySuggestions(pattern);
          break;
        case "currency":
          data = repo.getCurrencySuggestions(pattern);
          break;
        case "contractNo":
          data = repo.getContractNoSuggestions(pattern);
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

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Map<String, Object>> deleteContractById(@NonNull @PathVariable("id") Integer id) {
    var result = new HashMap<String, Object>();
    try {
      repo.findById(id).orElseThrow();
    } catch (Exception e) {
      result.put("error", "Contract doesn't exist");
      result.put("status", HttpStatus.NOT_FOUND.value());
      return ResponseEntity.ok(result);
    }
    repo.deleteById(id);
    result.put("message", String.format("Contract %d deleted successfully", id));
    result.put("status", HttpStatus.OK.value());
    return ResponseEntity.ok(result);
  }

  @PatchMapping(value = "{id}")
  public ResponseEntity<Map<String, Object>> updateContract(@NonNull @PathVariable("id") Integer id,
      @RequestBody UpdateContractRequest contract) {
    var result = new HashMap<String, Object>();
    Contract newContract = null;
    try {
      newContract = repo.findById(id).orElseThrow();
    } catch (Exception e) {
      result.put("status", HttpStatus.NOT_FOUND.value());
      result.put("error", "Contract doesn't exist");
      return ResponseEntity.ok(result);
    }
    newContract.contractNo = Objects.requireNonNullElse(contract.contractNo(), newContract.contractNo);
    newContract.company = Objects.requireNonNullElse(contract.company(), newContract.company);
    newContract.contractDate = Objects.requireNonNullElse(contract.contractDate(), newContract.contractDate);
    newContract.contractValue = Objects.requireNonNullElse(contract.contractValue(), newContract.contractValue);
    newContract.currency = Objects.requireNonNullElse(contract.currency(), newContract.currency);
    newContract.description = Objects.requireNonNullElse(contract.description(), newContract.description);
    try {
      newContract = repo.save(newContract);
    } catch (Exception e) {
      result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.put("error", e.getMessage());
      return ResponseEntity.ok(result);
    }
    result.put("status", HttpStatus.OK.value());
    result.put("message", String.format("Contract %d updated successfully", id));
    result.put("id", newContract.id);
    return ResponseEntity.ok(result);
  }

  @PostMapping(value = "{id}/upload")
  public ResponseEntity<Map<String, Object>> uploadContractFiles(@NonNull @PathVariable("id") Integer id,
      @RequestParam("contractFiles") List<MultipartFile> files) {
    var result = new HashMap<String, Object>();
    var contract = repo.findById(id).orElseThrow();
    Path destination;
    try {
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          destination = this.rootLocation
              .resolve(Paths.get(id.toString(), URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8")))
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
            var file = new ContractFiles();
            file.contract = contract;
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

  @GetMapping(value = "{id}/contractfiles")
  public ResponseEntity<Map<String, Object>> getContractFilesById(@NonNull @PathVariable("id") Integer id) {
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
      result.put("error", "Contract doesn't exist");
      result.put("success", false);
      result.put("status", HttpStatus.NOT_FOUND.value());
      return ResponseEntity.ok(result);
    }
  }

  @GetMapping(value = "{id}/contractErrandsFiles")
  public ResponseEntity<Map<String, Object>> getContractErrandsFilesById(@NonNull @PathVariable("id") Integer id) {
    var result = new HashMap<String, Object>();
    var innerResult = new HashMap<String, Object>();
    try {
      var data = repo.findById(id).orElseThrow().errandsfiles;
      innerResult.put("rows", data);
      result.put("data", innerResult);
      result.put("success", true);
      result.put("status", HttpStatus.OK.value());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      result.put("error", "Contract doesn't exist");
      result.put("success", false);
      result.put("status", HttpStatus.NOT_FOUND.value());
      return ResponseEntity.ok(result);
    }
  }

  @PostMapping(value = "{id}/errandsUpload")
  public ResponseEntity<Map<String, Object>> uploadContractErrandsFiles(@NonNull @PathVariable("id") Integer id,
      @RequestParam("errandsFiles") List<MultipartFile> files) {
    var result = new HashMap<String, Object>();
    var contract = repo.findById(id).orElseThrow();
    Path destination;
    try {
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          destination = this.rootLocation
              .resolve(
                  Paths.get(id.toString(), "errands", URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8")))
              .normalize();
          System.out.println(destination);
          var folder = new File(
              this.rootLocation.resolve(Paths.get(id.toString(), "errands")).normalize().toString());
          if (!folder.exists()) {
            System.out.println("Folder not found");
            System.out.println(folder.mkdirs());
          }
          try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, destination.toAbsolutePath(),
                StandardCopyOption.REPLACE_EXISTING);
            var file = new ErrandsFiles();
            file.contract = contract;
            file.fileName = URLDecoder.decode(multipartFile.getOriginalFilename(), "UTF-8");
            file.filePath = destination.toString();
            errandsFilesRepo.save(file);
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

  @PostMapping("/")
  public ResponseEntity<Map<String, Object>> addContract(@RequestBody AddContractRequest contract) {
    var result = new HashMap<String, Object>();
    Contract newContract = new Contract();
    newContract.contractNo = contract.contractNo();
    newContract.company = contract.company();
    newContract.contractDate = contract.contractDate();
    newContract.contractValue = contract.contractValue();
    newContract.currency = contract.currency();
    newContract.description = contract.description();
    try {
      newContract = repo.save(newContract);
    } catch (Exception e) {
      result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.put("error", e.getMessage());
      return ResponseEntity.ok(result);
    }
    result.put("status", HttpStatus.OK.value());
    result.put("message", "Contract added successfully");
    result.put("id", newContract.id);
    return ResponseEntity.ok(result);
  }

}

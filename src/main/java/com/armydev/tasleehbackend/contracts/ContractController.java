package com.armydev.tasleehbackend.contracts;

import static com.armydev.tasleehbackend.contracts.ContractSpecs.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/api/v1/contracts")
@AllArgsConstructor
public class ContractController {

    private final ContractRepo repo;

    // Error Handling

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllContracts(@RequestParam Map<String, String> searchParams)
            throws Exception {
        var result = new HashMap<String, Object>();
        // Get Desired Page
        int pageSize = searchParams.containsKey("pageSize") ? Integer.parseInt(searchParams.get("pageSize")) : 10;
        int currentPage = searchParams.containsKey("page") ? Integer.parseInt(searchParams.get("page")) : 1;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(Direction.DESC, "id"));
        // Get Filters as String Map
        Map<String, String> filters = searchParams.entrySet().stream()
                .filter(entry -> !(entry.getKey().equals("pageSize") || entry.getKey().equals("page")))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // Set Filters
        Specification<Contract> filter = null;
        for (var filterEntry : filters.entrySet()) {
            try {
                if (filter == null) {
                    filter = Specification.where(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));
                } else {
                    filter = Specification.where(filter)
                            .and(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));
                }
            } catch (Exception e) {
                result.put("status", HttpStatus.BAD_REQUEST.value());
                result.put("error",
                        String.format("Attribute %s cannot be found within %s", filterEntry.getKey(), "Contract"));
                return ResponseEntity.ok(result);
            }
        }
        // Get Results
        Page<Contract> contractsPage = repo.findAll(filter, pageable);
        result.put("rows", contractsPage.toList());
        result.put("count", contractsPage.getTotalPages());
        result.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(result);

    }

    // TODO: Add getTopSuggestions
    @GetMapping(path = "{id}")
    public ResponseEntity<Map<String, Object>> getContractById(@PathVariable("id") Integer id) {
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

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Map<String, Object>> deleteContractById(@PathVariable("id") Integer id) {
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

    @PatchMapping(path = "{id}")
    public ResponseEntity<Map<String, Object>> updateContract(@PathVariable("id") Integer id,
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
    // TODO: Add uploadContractFiles
    // TODO: Add getContractFiles
    // TODO: Add deleteContractFiles
    // TODO: Add downloadContractFile
    // TODO: Add uploadContractErrandsFiles
    // TODO: Add getContractErrandsFiles
    // TODO: Add deleteContractErrandsFiles
    // TODO: Add downloadContractErrandFile

    @PostMapping
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

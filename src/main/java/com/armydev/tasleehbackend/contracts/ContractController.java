package com.armydev.tasleehbackend.contracts;

import static com.armydev.tasleehbackend.contracts.ContractSpecs.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/api/v1/contract")
@AllArgsConstructor
public class ContractController {

    private final ContractRepo repo;

    // Error Handling

    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> getAllContracts(@RequestParam Map<String, String> searchParams)
            throws Exception {
        // TODO: add filtering
        var result = new HashMap<String, Object>();
        int pageSize = searchParams.containsKey("pageSize") ? Integer.parseInt(searchParams.get("pageSize")) : 10;
        int currentPage = searchParams.containsKey("page") ? Integer.parseInt(searchParams.get("page")) : 1;

        Map<String, String> filters = searchParams.entrySet().stream()
                .filter(entry -> !(entry.getKey().equals("pageSize") || entry.getKey().equals("page")))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(Direction.DESC, "id"));

        Specification<Contract> filter = null;
        for (var filterEntry : filters.entrySet()) {
            System.out.println(filterEntry.getKey());
            if (filter == null) {
                filter = Specification.where(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));
            } else {
                filter = Specification.where(filter)
                        .and(specsMap.get(filterEntry.getKey()).apply(filterEntry.getValue()));
            }
        }
        // Specification<Contract> filter =
        // Specification.where(specsMap.get("contractValue").apply("0.200"));
        Page<Contract> contractsPage = repo.findAll(filter, pageable);
        Map<String, Boolean> isError = new HashMap<>();
        /*
         * for (var filter : filters.entrySet()) {
         * if (!(filter.getKey().equals("dateBefore") ||
         * filter.getKey().equals("dateAfter"))) {
         * contracts = contracts.stream().filter(contract -> {
         * try {
         * return contract.getClass().getDeclaredField(filter.getKey())
         * .toString().contains(filter.getValue());
         * } catch (Exception e) {
         * isError.put(filter.getKey(), true);
         * return false;
         * }
         * }).toList();
         * }
         * }
         */
        if (isError.size() > 0) {
            throw new IllegalArgumentException(
                    String.format("Attribute %s is not within class", isError.keySet().toArray()[0]));
        }
        result.put("contracts", contractsPage.toList());
        result.put("numberOfPages", contractsPage.getTotalPages());
        /*
         * if (true)
         * throw new Exception("Testing Error Handling");
         */
        return ResponseEntity.ok(result);

    }

    // TODO: Add getTopSuggestions
    // TODO: Add filterContract
    // TODO: Add getContractByContractNo
    // TODO: Add deleteContractByContractNo
    // TODO: Add updateContract
    // TODO: Add uploadContractFiles
    // TODO: Add getContractFiles
    // TODO: Add deleteContractFiles
    // TODO: Add downloadContractFile
    // TODO: Add uploadContractErrandsFiles
    // TODO: Add getContractErrandsFiles
    // TODO: Add deleteContractErrandsFiles
    // TODO: Add downloadContractErrandFile

    @PostMapping("add")
    public ResponseEntity<String> addContract(@RequestBody AddContractRequest contract) {
        Contract newContract = new Contract();
        newContract.contractNo = contract.contractNo();
        newContract.company = contract.company();
        newContract.contractDate = contract.contractDate();
        newContract.contractValue = contract.contractValue();
        newContract.currency = contract.currency();
        newContract.description = contract.description();
        System.out.println(newContract);
        repo.save(newContract);
        return ResponseEntity.ok("Contract Added");
    }

}

package com.armydev.tasleehbackend.contracts;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Map<String, Object>> getAllContracts() throws Exception {
        // TODO: add filtering, pagination
        var result = new HashMap<String, Object>();

        List<Contract> contracts = repo.findAll();
        Collections.sort(contracts, (c1, c2) -> Integer.compare(c2.id, c1.id));
        result.put("contracts", contracts);
        result.put("numberOfPages", 0);
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

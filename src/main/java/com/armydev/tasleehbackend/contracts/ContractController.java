package com.armydev.tasleehbackend.contracts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/contract")
@AllArgsConstructor
public class ContractController {

    private final ContractRepo repo;

    @GetMapping("all")
    public List<Contract> getAllContracts() {
        return repo.findAll();
    }

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

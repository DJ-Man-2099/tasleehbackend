package com.armydev.tasleehbackend.contracts;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

}
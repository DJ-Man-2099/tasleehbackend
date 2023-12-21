package com.armydev.tasleehbackend.contracts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "contractfiles")
@Entity
public class ContractFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @ManyToOne
    @JoinColumn(name = "contractId", referencedColumnName = "id")
    public Contract contract;
    public String fileName;
    public String filePath;
}

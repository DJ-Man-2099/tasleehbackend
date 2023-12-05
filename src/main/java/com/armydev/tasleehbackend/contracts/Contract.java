package com.armydev.tasleehbackend.contracts;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "contracts")
@Entity
public class Contract {
    @Id
    public Integer id;
    // @Column(name = "contractNo")
    public String contractNo;
    public String description;
    // @Column(name = "contractValue")
    public double contractValue;
    @Enumerated(EnumType.STRING)
    public Company company;
    @Enumerated(EnumType.STRING)
    public Currency currency;
    // @Column(name = "contractDate")
    public Date contractDate;
    // @Column(name = "createdAt")
    public LocalDateTime createdAt;
    // @Column(name = "updatedAt")
    public LocalDateTime updatedAt;
}

enum Company {
    Soso,
    Nouri
}

enum Currency {
    EUR,
    USD
}
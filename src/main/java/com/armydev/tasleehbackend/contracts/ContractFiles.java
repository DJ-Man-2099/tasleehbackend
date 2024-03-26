package com.armydev.tasleehbackend.contracts;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "contractfiles")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractFiles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  @JsonIdentityReference(alwaysAsId = true)
  @ManyToOne
  @JoinColumn(name = "contractId", referencedColumnName = "id")
  public Contract contract;
  public String fileName;
  public String filePath;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;

  // Must Add For date adding
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  // Must add for date updating
  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}

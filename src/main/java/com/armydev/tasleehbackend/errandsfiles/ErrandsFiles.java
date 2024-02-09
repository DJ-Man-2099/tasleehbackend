package com.armydev.tasleehbackend.errandsfiles;

import java.time.LocalDateTime;

import com.armydev.tasleehbackend.contracts.Contract;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "errandsfiles")
@Entity
public class ErrandsFiles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  @JsonBackReference
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

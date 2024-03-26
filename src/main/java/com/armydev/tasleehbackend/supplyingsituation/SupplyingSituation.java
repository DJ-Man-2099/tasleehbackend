package com.armydev.tasleehbackend.supplyingsituation;

import java.time.LocalDateTime;

import com.armydev.tasleehbackend.contracts.Contract;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "supplyingsituations")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyingSituation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  public String product;

  public Integer totalQuantity;
  public Integer arrivedQuantity;
  public Integer remainedQuantity;
  public Float percentage;

  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "contractId", referencedColumnName = "id")
  public Contract contract;

  @Column(name = "contractId", insertable = false, updatable = false)
  public Integer contractId;

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

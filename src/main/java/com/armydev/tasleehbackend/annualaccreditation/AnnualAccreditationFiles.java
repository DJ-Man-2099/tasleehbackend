package com.armydev.tasleehbackend.annualaccreditation;

import java.time.LocalDateTime;

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

@Table(name = "annualaccreditionfiles")
@Entity
public class AnnualAccreditationFiles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "annualAccreditionId", referencedColumnName = "id")
  public AnnualAccreditation annualAccreditation;
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

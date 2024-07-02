package com.armydev.tasleehbackend.contracts;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditation;
import com.armydev.tasleehbackend.annualaccreditation.notification.AccreditationNotification;
import com.armydev.tasleehbackend.errandsfiles.ErrandsFiles;
import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetter;
import com.armydev.tasleehbackend.guaranteeletter.notification.GuaranteeNotification;
import com.armydev.tasleehbackend.supplyingsituation.SupplyingSituation;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "contracts")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Contract {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  // @Column(name = "contractNo")
  public String contractNo;
  public String description;
  // @Column(name = "contractValue")
  public double contractValue;
  public String company;
  public String currency;
  // @Column(name = "contractDate")
  public Date contractDate;
  // @Column(name = "createdAt")
  public LocalDateTime createdAt;
  // @Column(name = "updatedAt")
  public LocalDateTime updatedAt;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<ContractFiles> files;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<ErrandsFiles> errandsfiles;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<SupplyingSituation> supplyingSituation;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<AnnualAccreditation> annualAccreditation;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<AccreditationNotification> accreditationNotification;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<GuaranteeLetter> guaranteeLetters;

  @JsonIdentityReference(alwaysAsId = true)
  @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
  public List<GuaranteeNotification> guaranteeNotification;

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

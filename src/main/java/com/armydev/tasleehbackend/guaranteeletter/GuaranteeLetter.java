package com.armydev.tasleehbackend.guaranteeletter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.armydev.tasleehbackend.contracts.Contract;
import com.armydev.tasleehbackend.guaranteeletter.availability.GuaranteeLetterAvailability;
import com.armydev.tasleehbackend.guaranteeletter.files.GuaranteeLetterFiles;
import com.armydev.tasleehbackend.guaranteeletter.notification.GuaranteeNotification;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "guaranteeletters")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GuaranteeLetter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public String letterType;
	public String reportedBank;

	@Column(unique = true, nullable = false)
	public String letterSerialNo;

	public Integer guaranteeLetterValue;

	public Date latestDate;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	// Bug With using Nested Entities,
	// only the First Entity gets full data
	@JsonSerialize(using = CustomSerializer.class)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractId", referencedColumnName = "id")
	public Contract contract;

	@Column(name = "contractId", insertable = false, updatable = false)
	public Integer contractId;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "guaranteeLetter")
	public List<GuaranteeLetterFiles> files;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "guaranteeLetter")
	public List<GuaranteeLetterAvailability> actions;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToOne(mappedBy = "guaranteeLetter")
	public GuaranteeNotification notification;

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

package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.time.LocalDateTime;

import com.armydev.tasleehbackend.contracts.Contract;
import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "letternotifications")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GuaranteeNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public String description;

	public boolean isRead;

	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	@JsonIdentityReference(alwaysAsId = true)
	@OneToOne
	@JoinColumn(name = "guaranteeLetterId", referencedColumnName = "id")
	public GuaranteeLetter guaranteeLetter;

	@Column(name = "guaranteeLetterId", insertable = false, updatable = false)
	public Integer guaranteeLetterId;

	@JsonIdentityReference(alwaysAsId = true)
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
		guaranteeLetterId = guaranteeLetter.id;
		contractId = contract.id;
	}

	// Must add for date updating
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}

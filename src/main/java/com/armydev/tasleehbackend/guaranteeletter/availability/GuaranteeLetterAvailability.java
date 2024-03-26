package com.armydev.tasleehbackend.guaranteeletter.availability;

import java.sql.Date;
import java.time.LocalDateTime;

import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "guaranteeletteravailabilities")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GuaranteeLetterAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Enumerated(EnumType.STRING)
	public Action action;

	public Date action_date;

	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
	@JoinColumn(name = "guaranteeLetterId", referencedColumnName = "id")
	public GuaranteeLetter guaranteeLetter;

	@Column(name = "guaranteeLetterId", insertable = false, updatable = false)
	public Integer guaranteeLetterId;

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

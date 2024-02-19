package com.armydev.tasleehbackend.annualaccreditation.availability;

import java.sql.Date;
import java.time.LocalDateTime;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditation;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Table(name = "annualaccreditionavailabilities")
@Entity
public class AnnualAccreditationAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Enumerated(EnumType.STRING)
	public Action action;

	public Date action_date;

	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "annualAccreditionId", referencedColumnName = "id")
	public AnnualAccreditation annualAccreditation;

	@Column(name = "annualAccreditionId", insertable = false, updatable = false)
	public Integer annualAccreditionId;

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

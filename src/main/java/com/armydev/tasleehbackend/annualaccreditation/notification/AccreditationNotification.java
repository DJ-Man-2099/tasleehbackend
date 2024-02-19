package com.armydev.tasleehbackend.annualaccreditation.notification;

import java.time.LocalDateTime;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditation;
import com.armydev.tasleehbackend.contracts.Contract;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Table(name = "accreditionnotifications")
@Entity
public class AccreditationNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public String description;

	public boolean isRead;

	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "AnnualAccreditionId", referencedColumnName = "id")
	public AnnualAccreditation annualAccreditation;

	@Column(name = "AnnualAccreditionId", insertable = false, updatable = false)
	public Integer annualAccreditionId;

	@JsonBackReference
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

package com.armydev.tasleehbackend.annualaccreditation;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.armydev.tasleehbackend.contracts.Contract;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table(name = "annualaccreditions")
@Entity
public class AnnualAccreditation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public String openingBank;
	public String currency;

	@Column(unique = true, nullable = false)
	public Long accreditionNo;

	public Integer accreditionValue;

	public Date openingDate;
	public Date expiringDate;
	public LocalDateTime createdAt;
	public LocalDateTime updatedAt;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "contractId", referencedColumnName = "id")
	public Contract contract;

	@Column(name = "contractId", insertable = false, updatable = false)
	public Integer contractId;

	@JsonManagedReference
	@OneToMany(mappedBy = "annualAccreditation")
	public List<AnnualAccreditationFiles> files;

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

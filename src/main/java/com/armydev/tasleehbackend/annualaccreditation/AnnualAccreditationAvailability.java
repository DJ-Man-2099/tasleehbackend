package com.armydev.tasleehbackend.annualaccreditation;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "annualAccreditionId", referencedColumnName = "id")
	public AnnualAccreditation annualAccreditation;

}

package com.armydev.tasleehbackend.annualaccreditation;

import java.sql.Date;

public record UpsertAnnualAccreditationRequest(
		String openingBank,
		String currency,
		Long accreditionNo,
		Integer accreditionValue,
		Date openingDate,
		Date expiringDate) {
}

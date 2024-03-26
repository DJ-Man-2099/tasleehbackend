package com.armydev.tasleehbackend.guaranteeletter;

import java.sql.Date;

public record UpsertGuaranteeLetterRequest(
		String reportedBank,
		String letterType,
		String letterSerialNo,
		Integer guaranteeLetterValue,
		Date latestDate) {
}

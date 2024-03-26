package com.armydev.tasleehbackend.annualaccreditation.notification;

import java.time.LocalDateTime;

public record SentNotificationRecord(
		LocalDateTime createdAt,
		Integer AnnualAccreditionId,
		Integer contractId,
		String description,
		Boolean isRead) {
}

package com.armydev.tasleehbackend.annualaccreditation.notification;

public record SentNotificationRecord(
		String createdAt,
		Integer AnnualAccreditionId,
		Integer contractId,
		String description,
		Boolean isRead) {
}

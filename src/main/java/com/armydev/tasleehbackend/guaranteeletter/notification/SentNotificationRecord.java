package com.armydev.tasleehbackend.guaranteeletter.notification;

public record SentNotificationRecord(
		String createdAt,
		Integer GuaranteeLetterId,
		Integer contractId,
		String description,
		Boolean isRead) {
}

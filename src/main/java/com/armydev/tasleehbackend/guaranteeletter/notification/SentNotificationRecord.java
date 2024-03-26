package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.time.LocalDateTime;

public record SentNotificationRecord(
		LocalDateTime createdAt,
		Integer GuaranteeLetterId,
		Integer contractId,
		String description,
		Boolean isRead) {
}

package com.armydev.tasleehbackend.helpers;

import java.time.LocalDateTime;

import com.armydev.tasleehbackend.annualaccreditation.notification.AccreditationNotification;
import com.armydev.tasleehbackend.guaranteeletter.notification.GuaranteeNotification;

public class NotificationHelpers {
	private static String toFormattedDate(LocalDateTime dateTime) {
		return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public static com.armydev.tasleehbackend.guaranteeletter.notification.SentNotificationRecord createGuaranteeNotificationRecord(
			GuaranteeNotification notification) {
		var now = toFormattedDate(LocalDateTime.now());
		var guaranteeLetterId = notification.guaranteeLetterId;
		var contractId = notification.contractId;
		var description = notification.description;
		var isRead = notification.isRead;
		com.armydev.tasleehbackend.guaranteeletter.notification.SentNotificationRecord sentNotificationRecord = new com.armydev.tasleehbackend.guaranteeletter.notification.SentNotificationRecord(
				now,
				guaranteeLetterId,
				contractId,
				description,
				isRead);
		return sentNotificationRecord;
	}

	public static com.armydev.tasleehbackend.annualaccreditation.notification.SentNotificationRecord createAnnualNotificationRecord(
			AccreditationNotification notification) {
		var now = toFormattedDate(LocalDateTime.now());
		var annualAccreditionId = notification.annualAccreditionId;
		var contractId = notification.contractId;
		var description = notification.description;
		var isRead = notification.isRead;
		com.armydev.tasleehbackend.annualaccreditation.notification.SentNotificationRecord sentNotificationRecord = new com.armydev.tasleehbackend.annualaccreditation.notification.SentNotificationRecord(
				now,
				annualAccreditionId,
				contractId,
				description,
				isRead);
		return sentNotificationRecord;
	}
}

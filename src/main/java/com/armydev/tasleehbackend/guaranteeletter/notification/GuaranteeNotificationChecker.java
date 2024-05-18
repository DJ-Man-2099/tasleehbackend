package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetterRepo;
import com.armydev.tasleehbackend.helpers.NotificationHelpers;
import com.armydev.tasleehbackend.socketlistener.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GuaranteeNotificationChecker {
	private final GuaranteeNotificationRepo repo;
	private final SocketServer socket;
	private final GuaranteeLetterRepo glRepo;

	@Scheduled(fixedDelay = 1000 * 30)
	private void scheduleLetterDueCheck() {
		var allLetters = glRepo.findAll();
		LocalDateTime now = LocalDateTime.now();
		for (var letter : allLetters) {
			var period = now.until(letter.latestDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()),
					ChronoUnit.DAYS) + 1;
			var notification = repo.findByGuaranteeLetterIdAndContractId(letter.id, letter.contractId);
			System.out.println(period);
			if (period <= 30) {
				if (notification == null) {
					notification = new GuaranteeNotification();
					notification.guaranteeLetter = letter;
					notification.contract = letter.contract;
					notification.description = "Letter " + letter.letterSerialNo + " is due in " + period + " days";
					notification.isRead = false;
					repo.save(notification);
				} else if (notification.isRead) {
					notification.description = "Letter " + letter.letterSerialNo + " is due in " + period + " days";
					notification.isRead = false;
					repo.save(notification);
				} else {
					var newDescription = "Letter " + letter.letterSerialNo + " is due in " + period
							+ " days";
					if (!newDescription.equals(notification.description)) {
						notification.description = newDescription;
						repo.save(notification);
					} else {
						continue;
					}
				}

				SentNotificationRecord data = NotificationHelpers.createGuaranteeNotificationRecord(notification);
				try {
					socket.sendMessage(SocketServer.guaranteeEvent,
							data);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

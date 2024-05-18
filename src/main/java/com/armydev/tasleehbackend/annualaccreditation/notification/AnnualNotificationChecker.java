package com.armydev.tasleehbackend.annualaccreditation.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditationRepo;
import com.armydev.tasleehbackend.helpers.NotificationHelpers;
import com.armydev.tasleehbackend.socketlistener.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AnnualNotificationChecker {
	private final AccreditationNotificationRepo repo;
	private final SocketServer socket;
	private final AnnualAccreditationRepo aaRepo;

	@Scheduled(fixedDelay = 1000 * 30)
	private void scheduleAccreditionDueCheck() {
		System.out.println("Checking for due accreditations");
		var allAccreditations = aaRepo.findAll();
		LocalDateTime now = LocalDateTime.now();
		for (var accreditation : allAccreditations) {
			var period = now.until(accreditation.expiringDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()),
					ChronoUnit.DAYS) + 1;
			var notification = repo.findByAnnualAccreditionIdAndContractId(accreditation.id, accreditation.contractId);
			System.out.println(period);
			if (period <= 30) {
				if (notification == null) {
					notification = new AccreditationNotification();
					notification.annualAccreditation = accreditation;
					notification.contract = accreditation.contract;
					notification.isRead = false;
					notification.description = "Accredition " + accreditation.accreditionNo + " is due in " + period
							+ " days";
					repo.save(notification);
				} else if (notification.isRead) {
					notification.isRead = false;
					notification.description = "Accredition " + accreditation.accreditionNo + " is due in " + period
							+ " days";
					repo.save(notification);
				} else {
					var newDescription = "Accredition " + accreditation.accreditionNo + " is due in " + period
							+ " days";
					if (!newDescription.equals(notification.description)) {
						notification.description = newDescription;
						repo.save(notification);
					} else {
						continue;
					}
				}

				SentNotificationRecord data = NotificationHelpers.createAnnualNotificationRecord(notification);
				try {
					socket.sendMessage(SocketServer.annualEvent, data);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

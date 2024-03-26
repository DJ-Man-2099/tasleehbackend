package com.armydev.tasleehbackend.annualaccreditation.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.armydev.tasleehbackend.annualaccreditation.AnnualAccreditationRepo;
import com.armydev.tasleehbackend.socketlistener.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AnnualNotificationChecker {
	private final AccreditationNotificationRepo repo;
	private final SocketServer socket;
	private final AnnualAccreditationRepo aaRepo;
	Jackson2ObjectMapperBuilder mapperBuilder;

	@Scheduled(fixedDelay = 1000 * 30)
	private void scheduleAccreditionDueCheck() throws JsonProcessingException {
		ObjectMapper mapper = mapperBuilder.build();
		System.out.println("Checking for due accreditations");
		var allAccreditations = aaRepo.findAll();
		LocalDateTime now = LocalDateTime.now();
		for (var accreditation : allAccreditations) {
			var period = now.until(accreditation.expiringDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()),
					ChronoUnit.DAYS) + 1;
			var notification = repo.findByAnnualAccreditionId(accreditation.id);
			System.out.println(period);
			if (period <= 30 && notification == null) {
				var newNotification = new AccreditationNotification();
				newNotification.annualAccreditation = accreditation;
				newNotification.contract = accreditation.contract;
				newNotification.isRead = false;
				newNotification.description = "Accredition " + accreditation.accreditionNo + " is due in " + period
						+ " days";
				repo.save(newNotification);

				SentNotificationRecord data = new SentNotificationRecord(
						now,
						newNotification.annualAccreditation.id,
						newNotification.contract.id,
						newNotification.description,
						newNotification.isRead);
				var jsonString = mapper.writeValueAsString(data);
				socket.sendMessage("annualNotification",
						jsonString);
			}
		}
	}
}

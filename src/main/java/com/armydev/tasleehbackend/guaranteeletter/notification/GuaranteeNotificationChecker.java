package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetterRepo;
import com.armydev.tasleehbackend.socketlistener.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GuaranteeNotificationChecker {
	private final GuaranteeNotificationRepo repo;
	private final SocketServer socket;
	private final GuaranteeLetterRepo glRepo;
	Jackson2ObjectMapperBuilder mapperBuilder;

	@Scheduled(fixedDelay = 1000 * 30)
	private void scheduleLetterDueCheck() throws JsonProcessingException {
		ObjectMapper mapper = mapperBuilder.build();
		System.out.println("Checking for due accreditations");
		var allLetters = glRepo.findAll();
		LocalDateTime now = LocalDateTime.now();
		for (var letter : allLetters) {
			var period = now.until(letter.latestDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()),
					ChronoUnit.DAYS) + 1;
			var notification = repo.findByGuaranteeLetterId(letter.id);
			System.out.println(period);
			if (period <= 30 && notification == null) {
				var newNotification = new GuaranteeNotification();
				newNotification.guaranteeLetter = letter;
				newNotification.contract = letter.contract;
				newNotification.isRead = false;
				newNotification.description = "Letter " + letter.letterSerialNo + " is due in " + period
						+ " days";
				repo.save(newNotification);

				SentNotificationRecord data = new SentNotificationRecord(
						now,
						newNotification.guaranteeLetter.id,
						newNotification.contract.id,
						newNotification.description,
						newNotification.isRead);
				var jsonString = mapper.writeValueAsString(data);
				socket.sendMessage("guaranteeNotification",
						jsonString);
			}
		}
	}
}

package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.armydev.tasleehbackend.guaranteeletter.GuaranteeLetter;
import com.armydev.tasleehbackend.helpers.NotificationHelpers;
import com.armydev.tasleehbackend.socketlistener.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@RequestMapping("guaranteeLettersNotifications")
@RestController
@AllArgsConstructor
public class GuaranteeNotificationController {

	private final GuaranteeNotificationRepo repo;
	private final SocketServer socket;

	@PostMapping("/{contract}/{annualAccredId}")
	public ResponseEntity<Map<String, Object>> setReadNotification(
			@NonNull @PathVariable("annualAccredId") Integer id,
			@NonNull @PathVariable("contract") Integer contractId) {
		var result = new HashMap<String, Object>();
		GuaranteeNotification notification;
		try {
			notification = repo.findByGuaranteeLetterIdAndContractId(id, contractId);
		} catch (Exception e) {
			result.put("error", "Notification not found");
			result.put("status", HttpStatus.NOT_FOUND.value());
			return ResponseEntity.ok().body(result);
		}
		if (notification != null) {
			notification.isRead = true;
			repo.save(notification);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllLettersNotifions() {
		var result = new HashMap<String, Object>();
		var notifications = repo.findAll();
		var readNotificationsCount = repo.findAllByIsRead(true).size();
		result.put("rows", notifications);
		result.put("count", notifications.size());
		result.put("readNotificationsCount", readNotificationsCount);
		return ResponseEntity.ok(result);
	}

	public void deleteNotification(GuaranteeLetter letter) {
		var notification = repo.findByGuaranteeLetterIdAndContractId(letter.id, letter.contractId);
		if (notification != null) {
			repo.delete(notification);
		}
	}

	public void upsertNotification(GuaranteeLetter letter) {
		LocalDateTime now = LocalDateTime.now();
		var newNotification = repo.findByGuaranteeLetterIdAndContractId(letter.id, letter.contractId);
		if (newNotification == null) {
			newNotification = new GuaranteeNotification();
			newNotification.guaranteeLetter = letter;
			newNotification.contract = letter.contract;
		}
		newNotification.description = "Letter %s is due in %d days".formatted(letter.letterSerialNo,
				now.until(letter.latestDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()),
						java.time.temporal.ChronoUnit.DAYS));
		repo.save(newNotification);
		SentNotificationRecord data = NotificationHelpers.createGuaranteeNotificationRecord(newNotification);
		try {
			socket.sendMessage(SocketServer.guaranteeEvent, data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: getAnnualAccreditionNotifications
	// TODO: scheduleAccreditionDueCheck

}

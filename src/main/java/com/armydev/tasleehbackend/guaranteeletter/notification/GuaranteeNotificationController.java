package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@RequestMapping("guaranteeLettersNotifications")
@RestController
@AllArgsConstructor
public class GuaranteeNotificationController {

	private final GuaranteeNotificationRepo repo;

	@PostMapping("/{annualAccredId}")
	public ResponseEntity<Map<String, Object>> setReadNotification(
			@NonNull @PathVariable("annualAccredId") Integer id) {
		var result = new HashMap<String, Object>();
		GuaranteeNotification notification;
		try {
			notification = repo.findById(id).orElseThrow();
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

	// TODO: getAnnualAccreditionNotifications
	// TODO: scheduleAccreditionDueCheck

}

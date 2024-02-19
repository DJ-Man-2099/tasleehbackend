package com.armydev.tasleehbackend.annualaccreditation.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccreditationNotificationRepo
		extends JpaRepository<AccreditationNotification, Integer>, JpaSpecificationExecutor<AccreditationNotification> {

	List<AccreditationNotification> findAllByIsRead(boolean b);

	AccreditationNotification findByAnnualAccreditionId(Integer id);

}

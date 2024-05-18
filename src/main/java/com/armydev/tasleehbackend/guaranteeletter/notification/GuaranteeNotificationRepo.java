package com.armydev.tasleehbackend.guaranteeletter.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuaranteeNotificationRepo
		extends JpaRepository<GuaranteeNotification, Integer>, JpaSpecificationExecutor<GuaranteeNotification> {

	List<GuaranteeNotification> findAllByIsRead(boolean b);

	GuaranteeNotification findByGuaranteeLetterIdAndContractId(Integer letterId, Integer contractId);

}

package kr.co.kcp.backendcoding.work.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
	private static final String NOTIFY_TEMPLATE = "{} : {} 예약완료";

	public void notify(String paymentType, String reservationId) {
		log.info(NOTIFY_TEMPLATE, paymentType, reservationId);
	}
}
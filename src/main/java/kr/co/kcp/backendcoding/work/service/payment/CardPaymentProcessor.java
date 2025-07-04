package kr.co.kcp.backendcoding.work.service.payment;

import java.util.UUID;

import org.springframework.stereotype.Component;

import kr.co.kcp.backendcoding.work.dto.ReservationRequestDto;
import kr.co.kcp.backendcoding.work.entity.OrderReservation;
import kr.co.kcp.backendcoding.work.repository.OrderReservationRepository;
import kr.co.kcp.backendcoding.work.service.NotificationService;

@Component
public class CardPaymentProcessor implements PaymentProcessor {

	private final OrderReservationRepository repository;
	private final NotificationService notificationService;

	public CardPaymentProcessor(OrderReservationRepository repository, NotificationService notificationService) {
		this.repository = repository;
		this.notificationService = notificationService;
	}

	@Override
	public boolean supports(String paymentType) {
		return "CARD".equalsIgnoreCase(paymentType);
	}
	
	@Override
	public String process(ReservationRequestDto dto) {
		String reservationId = generateId();
		OrderReservation res = new OrderReservation();
		res.setReservation_id(reservationId);
		res.setPayment_type(dto.getPayment_type());
		res.setStore_code(dto.getStore_code());
		res.setOrder_amount(dto.getOrder_amount());
		res.setDiscount_amount(dto.getDiscount_amount());
		res.setPayment_amount(dto.getPayment_amount());

		repository.save(res);
		notificationService.notify(dto.getPayment_type(), reservationId);
		return reservationId;
	}

	private String generateId() {
		return "R" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
	}
}

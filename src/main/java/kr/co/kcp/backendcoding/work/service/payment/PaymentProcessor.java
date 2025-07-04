package kr.co.kcp.backendcoding.work.service.payment;

import kr.co.kcp.backendcoding.work.dto.ReservationRequestDto;

public interface PaymentProcessor {
	boolean supports(String paymentType); // 결제 타입 매칭
	String process(ReservationRequestDto requestDto);
}

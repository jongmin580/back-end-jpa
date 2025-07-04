package kr.co.kcp.backendcoding.work.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcp.backendcoding.work.dto.ReservationRequestDto;
import kr.co.kcp.backendcoding.work.service.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final List<PaymentProcessor> processors;

	public String reserve(ReservationRequestDto dto) {
		PaymentProcessor processor = findProcessor(dto.getPayment_type());
		return processor.process(dto);
	}

	/**
	 * 결제 타입에 따라 적절한 Processor를 반환
	 * @param type 결제 타입 (예: "CARD", "CASH")
	 * @return 해당 타입을 지원하는 Processor
	 * @throws IllegalArgumentException 지원하지 않는 타입일 경우
	 */
	private PaymentProcessor findProcessor(String type) {
		return processors.stream()
			.filter(p -> p.supports(type))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 결제 타입입니다."));
	}
}
package kr.co.kcp.backendcoding.work.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kcp.backendcoding.work.common.CommonResponse;
import kr.co.kcp.backendcoding.work.dto.ReservationRequestDto;
import kr.co.kcp.backendcoding.work.service.ReservationService;
import lombok.RequiredArgsConstructor;

/**
 * 주문 예약 기능 컨트롤러
 * URI: /reservations
 * 기능: 결제 타입에 따라 예약을 처리하고 예약 ID를 응답으로 반환
 */
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	/**
	 * 주문 예약 API
	 * 결제 타입(CARD, CASH 등)에 따라 예약을 저장하고, 성공 시 예약 ID를 반환
	 *
	 * @param dto ReservationRequestDto - 예약 요청 정보 (payment_type, store_code 등)
	 * @return 예약 성공 시 예약 ID 포함 응답, 실패 시 적절한 오류 메시지 반환
	 */
	@PostMapping
	public ResponseEntity<?> reserve(@RequestBody ReservationRequestDto dto) {
		if (dto.getPayment_type() == null || dto.getPayment_type().isBlank()) {
			return ResponseEntity.badRequest().body(
				CommonResponse.error(400, "결제 타입은 필수입니다.")
			);
		}

		try {
			String reservationId = reservationService.reserve(dto);
			return ResponseEntity.ok(CommonResponse.success(Map.of("reservation_id", reservationId)));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(
				CommonResponse.error(400, e.getMessage())
			);
		}
	}
}

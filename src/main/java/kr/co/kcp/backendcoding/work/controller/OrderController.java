package kr.co.kcp.backendcoding.work.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kcp.backendcoding.work.common.CommonResponse;
import kr.co.kcp.backendcoding.work.service.OrderService;
import lombok.RequiredArgsConstructor;

/**
 * 주문 정보를 조회하는 컨트롤러
 * URI: /orders
 * 기능: 주문 ID를 기반으로 주문 상세 정보를 조회함
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	/**
	 * 주문 ID로 주문 상세 정보를 조회하는 GET API
	 * 
	 * @param orderId - 6자리 문자열 형식의 주문 ID
	 * @return 성공 시 주문 상세 정보, 실패 시 에러 코드 및 메시지
	 */
	@GetMapping
	public ResponseEntity<?> getOrder(@RequestParam String orderId) {
		if (orderId == null || orderId.length() != 6) {
			return ResponseEntity.badRequest().body(CommonResponse.error(400, "orderId는 6자리 문자열이어야 합니다."));
		}

		try {
			var data = orderService.getOrder(orderId);
			return ResponseEntity.ok(CommonResponse.success(data));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(CommonResponse.error(404, e.getMessage()));
		}
	}
}

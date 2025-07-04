package kr.co.kcp.backendcoding.work.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 주문 예약 요청 DTO
 * - 사용 목적: 클라이언트로부터 전달받은 예약 정보를 서버에서 처리하기 위한 데이터 전달 객체
 */
@Getter
@Setter
public class ReservationRequestDto {
	private String payment_type;
	private String store_code;
	private int order_amount;
	private int discount_amount;
	private int payment_amount;
}

package kr.co.kcp.backendcoding.work.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 예약(OrderReservation) 엔티티 클래스
 * - 주문 예약 정보를 저장하는 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "order_reservations")
@Getter
@Setter
@NoArgsConstructor
public class OrderReservation {
	/**
	 * 예약 고유 ID (기본 키)
	 * - 9자리 문자열 (예: R + 8자리 랜덤 영숫자)
	 */
	@Id
	private String reservation_id; // 9자리 랜덤

	 /** 결제 타입 (예: CARD, CASH) */
	private String payment_type;

	/** 매장 코드 */
	private String store_code;

	/** 주문 금액 */
	private int order_amount;

	/** 할인 금액 */
	private int discount_amount;

	/** 결제 금액 */
	private int payment_amount;
}

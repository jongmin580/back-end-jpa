package kr.co.kcp.backendcoding.work.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 주문 결제(OrderPayment) 엔티티 클래스
 * - 특정 주문의 결제 정보를 나타냅니다.
 */
@Entity
@Getter
@Table(name = "order_payments")
public class OrderPayment {
	/** 결제 고유 ID (기본 키) */
	@Id
	private int paymentId;

	/** 결제 방식 (예: CARD, CASH 등) */
	private String paymentMethod;
	/** 결제 일시 */
	private String paymentDate;

	/**
	 * 이 결제가 연결된 주문 정보
	 * - order_id 외래키를 통해 orders 테이블과 연결
	 */
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
}

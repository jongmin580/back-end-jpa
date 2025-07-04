package kr.co.kcp.backendcoding.work.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 주문(Order) 엔티티 클래스
 * - 주문 금액, 주문 날짜, 연관된 메뉴와 결제 정보를 포함합니다.
 */
@Entity
@Getter
@Table(name = "orders")
public class Order {
	 /** 주문 ID (Primary Key) */
	@Id
	private String orderId;

	/** 총 주문 금액 */
	private int orderAmount;

	/** 주문 일자 */
	private String orderDate;

	/** 
	 * 주문에 포함된 메뉴 목록
	 * - OrderMenu.order 필드를 통해 양방향 매핑
	 * - LAZY 로딩 사용: 실제 접근 시에만 조회
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderMenu> orderMenus;

	/** 
	 * 주문에 사용된 결제 내역 목록
	 * - OrderPayment.order 필드를 통해 양방향 매핑
	 * - LAZY 로딩 사용: 실제 접근 시에만 조회
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderPayment> orderPayments;
}
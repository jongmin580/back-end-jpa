package kr.co.kcp.backendcoding.work.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 주문 메뉴(OrderMenu) 엔티티 클래스
 * - 주문에 포함된 개별 메뉴 항목 정보를 나타냅니다.
 */
@Entity
@Getter
@Table(name = "order_menus")
public class OrderMenu {
	/** 메뉴 항목의 고유 ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 메뉴 상품명 */
	private String productName;

	/** 메뉴 가격 */
	private int price;

	/**
	 * 이 메뉴가 속한 주문 정보
	 * - order_id 외래키를 통해 orders 테이블과 연결
	 * - 단방향 연관관계 설정
	 */
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
}

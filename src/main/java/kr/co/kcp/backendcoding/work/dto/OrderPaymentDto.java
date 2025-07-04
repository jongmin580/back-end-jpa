package kr.co.kcp.backendcoding.work.dto;

/**
 * 주문 결제 DTO
 * - 사용 목적: 주문 상세 조회 시 해당 주문에 대한 결제 정보를 응답에 포함하기 위한 DTO
 */
public record OrderPaymentDto(
	int paymentId,
	String paymentMethod,
	String paymentDate
) {}

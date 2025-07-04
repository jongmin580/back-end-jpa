package kr.co.kcp.backendcoding.work.dto;

import java.util.List;

/**
 * 주문 상세 응답 DTO
 * - 사용 목적: 주문 상세 조회 API 응답 시 주문의 전체 정보를 전달하기 위한 DTO
 */
public record OrderResponseDto(
	int orderAmount,
	String orderDate,
	List<OrderMenuDto> orderMenus,
	List<OrderPaymentDto> orderPayments
) {}
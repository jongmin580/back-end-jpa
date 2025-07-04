package kr.co.kcp.backendcoding.work.dto;

/**
 * 주문 메뉴 DTO
 * - 사용 목적: 주문 상세 조회 시 주문에 포함된 개별 메뉴 정보를 응답에 포함하기 위한 DTO
 */
public record OrderMenuDto(
	String productName,
	int price
) {}
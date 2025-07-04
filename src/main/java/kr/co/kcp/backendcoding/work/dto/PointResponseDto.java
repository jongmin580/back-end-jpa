package kr.co.kcp.backendcoding.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 조회 응답 DTO
 * - 사용 목적: 포인트 조회 API 응답 시 클라이언트에게 반환할 포인트 정보 전달
 */
@Getter
@AllArgsConstructor
public class PointResponseDto {
	private int orderAmt;
	private int myPoint;
	private int useMinPoint;
	private int useMaxPoint;
	private int useUnitPoint;
}
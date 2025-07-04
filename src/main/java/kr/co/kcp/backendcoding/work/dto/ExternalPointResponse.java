package kr.co.kcp.backendcoding.work.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 외부 포인트 조회 API 응답 DTO
 * - 사용 목적: https://test-qr.orderpick.kr/test/api/work/point 호출 결과 매핑
 * - 설명: 외부 시스템에서 반환하는 포인트 정보 응답 필드를 매핑하는 용도
 */
@Getter
@Setter
public class ExternalPointResponse {
	private int orderAmt;
	private int myPoint;
	private int useMinPoint;
	private int useMaxPoint;
	private int useUnitPoint;
}
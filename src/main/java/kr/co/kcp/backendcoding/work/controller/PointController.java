package kr.co.kcp.backendcoding.work.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kcp.backendcoding.work.common.CommonResponse;
import kr.co.kcp.backendcoding.work.dto.PointResponseDto;
import kr.co.kcp.backendcoding.work.service.PointService;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 정보를 조회하는 컨트롤러
 * URI: /points
 * 기능: 외부 API를 호출하여 포인트 정보를 조회하고 결과를 JSON 형태로 반환
 */
@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

	private final PointService pointService;

	/**
	 * 포인트 타입에 따른 포인트 정보 조회 API
	 *
	 * @param pointType - "CODE_A" ~ "CODE_G" 형식의 포인트 타입 (필수)
	 * @return 외부 API 응답을 기반으로 한 포인트 정보 또는 오류 메시지
	 */
	@GetMapping
	public ResponseEntity<?> getPoint(@RequestParam(required = false) String pointType) {
		if (pointType == null || pointType.isBlank()) {
			return ResponseEntity.badRequest().body(
				CommonResponse.error(400, "pointType 파라미터는 필수입니다.")
			);
		}

		try {
			PointResponseDto result = pointService.getPointInfo(pointType);
			return ResponseEntity.ok(CommonResponse.success(result));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(
				CommonResponse.error(400, e.getMessage())
			);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(
				CommonResponse.error(500, "외부 API 호출 실패")
			);
		}
	}
}

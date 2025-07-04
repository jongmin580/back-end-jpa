package kr.co.kcp.backendcoding.work.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kr.co.kcp.backendcoding.work.dto.ExternalPointResponse;
import kr.co.kcp.backendcoding.work.dto.PointResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {
	private static final Logger log = LoggerFactory.getLogger(PointService.class);

	private static final String EXTERNAL_API_URL = "https://test-qr.orderpick.kr/test/api/work/point";
	private static final int MAX_RETRIES = 3;

	private final RestTemplate restTemplate;

	public PointResponseDto getPointInfo(String pointType) {
		String typeCode = extractTypeCode(pointType);
		String url = buildUrl(typeCode);

		long start = System.currentTimeMillis();
		int retryCount = 0;

		while (retryCount < MAX_RETRIES) {
			try {
				return fetchPointData(url);
			} catch (Exception e) {
				retryCount++;
				log.warn("재시도 {}회 - {}: {}", retryCount, e.getClass().getSimpleName(), e.getMessage());
				if (retryCount >= MAX_RETRIES) {
					long end = System.currentTimeMillis();
					log.error("외부 API 호출 실패 - 총 소요 시간(ms): {}", (end - start));
					throw new RuntimeException("외부 API 호출 실패: " + e.getMessage());
				}
			}
		}

		throw new IllegalStateException("도달할 수 없는 코드");
	}

	private String extractTypeCode(String pointType) {
		if (pointType == null || !pointType.startsWith("CODE_") || pointType.length() != 6) {
			throw new IllegalArgumentException("pointType은 CODE_A ~ CODE_G 형식이어야 합니다.");
		}
		return pointType.substring(5); // CODE_A → A
	}

	private String buildUrl(String typeCode) {
		return UriComponentsBuilder.fromHttpUrl(EXTERNAL_API_URL)
			.queryParam("type", typeCode)
			.toUriString();
	}

	private PointResponseDto fetchPointData(String url) {
		ResponseEntity<ExternalPointResponse> response = restTemplate.getForEntity(url, ExternalPointResponse.class);
		ExternalPointResponse external = response.getBody();

		if (external == null) {
			throw new RuntimeException("외부 응답이 null입니다.");
		}

		return mapToPointResponseDto(external);
	}

	private PointResponseDto mapToPointResponseDto(ExternalPointResponse external) {
		return new PointResponseDto(
			external.getOrderAmt(),
			external.getMyPoint(),
			external.getUseMinPoint(),
			external.getUseMaxPoint(),
			external.getUseUnitPoint()
		);
	}
}

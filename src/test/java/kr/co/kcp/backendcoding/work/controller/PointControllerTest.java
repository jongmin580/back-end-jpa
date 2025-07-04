package kr.co.kcp.backendcoding.work.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.co.kcp.backendcoding.work.dto.PointResponseDto;
import kr.co.kcp.backendcoding.work.service.PointService;

/**
 * 단위 테스트
 * 기능: 포인트 정보 조회 외부 API 호출
 * 검증항목:
 * - 유효한 pointType 요청 시 200 OK와 포인트 정보 반환
 * - pointType 누락 시 400 Bad Request 반환
 * - 잘못된 형식의 pointType 시 400 Bad Request 반환
 * - 외부 API 실패 시 500 Internal Server Error 반환
 */
@WebMvcTest(PointController.class)
class PointControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PointService pointService;

	@Test
	@DisplayName("정상적인 pointType 요청 시 200 OK와 데이터 반환")
	void validPointType_returnsPointInfo() throws Exception {
		PointResponseDto dto = new PointResponseDto(15000, 5000, 1000, 3000, 500);

		given(pointService.getPointInfo("CODE_B")).willReturn(dto);

		mockMvc.perform(MockMvcRequestBuilders.get("/points")
				.param("pointType", "CODE_B")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(0))
			.andExpect(jsonPath("$.message").value("성공"))
			.andExpect(jsonPath("$.data.orderAmt").value(15000))
			.andExpect(jsonPath("$.data.myPoint").value(5000))
			.andExpect(jsonPath("$.data.useMinPoint").value(1000))
			.andExpect(jsonPath("$.data.useMaxPoint").value(3000))
			.andExpect(jsonPath("$.data.useUnitPoint").value(500));
	}

	@Test
	@DisplayName("pointType 누락 시 400 반환")
	void missingPointType_returnsBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/points")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("pointType 파라미터는 필수입니다."));
	}

	@Test
	@DisplayName("형식이 잘못된 pointType 전달 시 400 반환")
	void invalidPointType_returnsBadRequest() throws Exception {
		given(pointService.getPointInfo("INVALID"))
			.willThrow(new IllegalArgumentException("pointType은 CODE_A ~ CODE_G 형식이어야 합니다."));

		mockMvc.perform(MockMvcRequestBuilders.get("/points")
				.param("pointType", "INVALID")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("pointType은 CODE_A ~ CODE_G 형식이어야 합니다."));
	}

	@Test
	@DisplayName("외부 API 호출 실패 시 500 반환")
	void externalApiFailure_returnsInternalServerError() throws Exception {
		given(pointService.getPointInfo("CODE_B"))
			.willThrow(new RuntimeException("외부 API 호출 실패"));

		mockMvc.perform(MockMvcRequestBuilders.get("/points")
				.param("pointType", "CODE_B")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value(500))
			.andExpect(jsonPath("$.message").value("외부 API 호출 실패"));
	}
}

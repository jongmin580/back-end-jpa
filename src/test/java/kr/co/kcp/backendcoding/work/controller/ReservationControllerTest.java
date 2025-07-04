package kr.co.kcp.backendcoding.work.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.kcp.backendcoding.work.dto.ReservationRequestDto;
import kr.co.kcp.backendcoding.work.service.ReservationService;

/**
 * 단위 테스트
 * 기능: 주문 예약 기능에 대한 검증
 * 검증 항목:
 * - 결제 타입 누락 시 400 반환
 * - 지원하지 않는 결제 타입 시 400 반환(CARD, CASH)
 * - 정상예약 요청 시 200 및 예약 ID 반환
 */
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReservationService reservationService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("결제 타입이 누락된 경우 400 Bad Request 반환")
	void missingPaymentType_returns400() throws Exception {
		ReservationRequestDto dto = new ReservationRequestDto();
		dto.setStore_code("S001");
		dto.setOrder_amount(10000);
		dto.setDiscount_amount(1000);
		dto.setPayment_amount(9000);
		dto.setPayment_type(""); // 빈 문자열

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("결제 타입은 필수입니다."));
	}

	@Test
	@DisplayName("지원하지 않는 결제 타입일 경우 400 반환")
	void unsupportedPaymentType_returns400() throws Exception {
		ReservationRequestDto dto = new ReservationRequestDto();
		dto.setPayment_type("BITCOIN"); // 지원되지 않는 결제 타입
		dto.setStore_code("S001");
		dto.setOrder_amount(10000);
		dto.setDiscount_amount(1000);
		dto.setPayment_amount(9000);

		given(reservationService.reserve(any(ReservationRequestDto.class))).willThrow(new IllegalArgumentException("지원하지 않는 결제 타입입니다."));

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("지원하지 않는 결제 타입입니다."));
	}

	@Test
	@DisplayName("정상적인 예약 요청 시 200 OK와 예약 ID 반환")
	void validReservationRequest_returns200AndReservationId() throws Exception {
		ReservationRequestDto dto = new ReservationRequestDto();
		dto.setPayment_type("CARD");
		dto.setStore_code("S001");
		dto.setOrder_amount(10000);
		dto.setDiscount_amount(1000);
		dto.setPayment_amount(9000);

		given(reservationService.reserve(any(ReservationRequestDto.class))).willReturn("RABCDEFGH");

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(0))
			.andExpect(jsonPath("$.message").value("성공"))
			.andExpect(jsonPath("$.data.reservation_id").value("RABCDEFGH"));
	}
}
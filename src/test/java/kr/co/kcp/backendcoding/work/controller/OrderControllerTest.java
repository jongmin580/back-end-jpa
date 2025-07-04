package kr.co.kcp.backendcoding.work.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.co.kcp.backendcoding.work.dto.OrderMenuDto;
import kr.co.kcp.backendcoding.work.dto.OrderPaymentDto;
import kr.co.kcp.backendcoding.work.dto.OrderResponseDto;
import kr.co.kcp.backendcoding.work.service.OrderService;

/**
 * 단위 테스트
 * 기능: 주문정보 조회
 * 검증항목:
 * - orderId 길이 6자 제한 400 반환
 * - 존재하지 않는 주문 ID는 404 반환
 * - 정상 주문 조회 시 200 OK 반환
 */
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Test
	@DisplayName("orderId 길이가 6자가 아니면 400 반환")
	void invalidOrderId_returns400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/orders?orderId=123"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("orderId는 6자리 문자열이어야 합니다."));
	}

	@Test
	@DisplayName("존재하지 않는 주문 ID는 404 반환")
	void orderNotFound_returns404() throws Exception {
		given(orderService.getOrder("ZZZZZZ"))
			.willThrow(new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders?orderId=ZZZZZZ"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value(404))
			.andExpect(jsonPath("$.message").value("주문 정보를 찾을 수 없습니다."));
	}

	@Test
	@DisplayName("정상 주문 조회 시 200 OK 반환")
	void validOrderId_returnsOrderData() throws Exception {
		OrderMenuDto menuDto = new OrderMenuDto("치킨", 18000);
		OrderPaymentDto paymentDto = new OrderPaymentDto(1, "카드", "2025-07-01");
		OrderResponseDto responseDto = new OrderResponseDto(
			18000, "2025-07-01", List.of(menuDto), List.of(paymentDto)
		);

		given(orderService.getOrder("ABC123")).willReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.get("/orders?orderId=ABC123"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(0))
			.andExpect(jsonPath("$.message").value("성공"))
			.andExpect(jsonPath("$.data.orderAmount").value(18000))
			.andExpect(jsonPath("$.data.orderDate").value("2025-07-01"))
			.andExpect(jsonPath("$.data.orderMenus[0].productName").value("치킨"))
			.andExpect(jsonPath("$.data.orderMenus[0].price").value(18000))
			.andExpect(jsonPath("$.data.orderPayments[0].paymentMethod").value("카드"))
			.andExpect(jsonPath("$.data.orderPayments[0].paymentDate").value("2025-07-01"));
	}
}

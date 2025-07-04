package kr.co.kcp.backendcoding.work.service;

import org.springframework.stereotype.Service;

import kr.co.kcp.backendcoding.work.dto.OrderResponseDto;
import kr.co.kcp.backendcoding.work.repository.OrderRepository;
import kr.co.kcp.backendcoding.work.dto.OrderMenuDto;
import kr.co.kcp.backendcoding.work.dto.OrderPaymentDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	
	public OrderResponseDto getOrder(String orderId) {
		var order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

		var menuDtos = order.getOrderMenus().stream()
			.map(m -> new OrderMenuDto(m.getProductName(), m.getPrice()))
			.toList();

		var paymentDtos = order.getOrderPayments().stream()
			.map(p -> new OrderPaymentDto(p.getPaymentId(), p.getPaymentMethod(), p.getPaymentDate()))
			.toList();

		return new OrderResponseDto(order.getOrderAmount(), order.getOrderDate(), menuDtos, paymentDtos);
	}
}

package kr.co.kcp.backendcoding.work.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.kcp.backendcoding.work.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
	Optional<Order> findByOrderId(String orderId);
}

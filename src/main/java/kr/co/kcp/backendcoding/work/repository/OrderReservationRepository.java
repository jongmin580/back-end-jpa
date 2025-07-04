package kr.co.kcp.backendcoding.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.kcp.backendcoding.work.entity.OrderReservation;

public interface OrderReservationRepository extends JpaRepository<OrderReservation, String> {
}

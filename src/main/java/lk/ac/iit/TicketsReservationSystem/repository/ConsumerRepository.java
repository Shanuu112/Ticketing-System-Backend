package lk.ac.iit.TicketsReservationSystem.repository;

import lk.ac.iit.TicketsReservationSystem.entity.ConsumerDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<ConsumerDAO,Long> {

}

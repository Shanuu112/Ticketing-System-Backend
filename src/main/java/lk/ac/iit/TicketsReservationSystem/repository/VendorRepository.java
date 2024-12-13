package lk.ac.iit.TicketsReservationSystem.repository;

import lk.ac.iit.TicketsReservationSystem.entity.VendorDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<VendorDAO,Long> {
}

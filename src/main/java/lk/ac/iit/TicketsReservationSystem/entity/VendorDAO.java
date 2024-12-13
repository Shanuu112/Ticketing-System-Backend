package lk.ac.iit.TicketsReservationSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor")
public class VendorDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private int vendorId;

    @Column(name = "ticket_id", nullable = false)
    private int ticketId;

    public VendorDAO(int vendorId, int ticketId) {
        this.vendorId = vendorId;
        this.ticketId = ticketId;
    }

    public VendorDAO() {

    }
}

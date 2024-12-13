package lk.ac.iit.TicketsReservationSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer")
public class ConsumerDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consumer_id", nullable = false)
    private int consumerId;

    @Column(name = "ticket_id", nullable = false)
    private int ticketId;

    public ConsumerDAO(){}

    public ConsumerDAO(int consumerId, int ticketId){
        this.consumerId = consumerId;
        this.ticketId = ticketId;
    }
}

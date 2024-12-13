package lk.ac.iit.TicketsReservationSystem.model;

import lk.ac.iit.TicketsReservationSystem.entity.ConsumerDAO;
import lk.ac.iit.TicketsReservationSystem.entity.VendorDAO;
import lk.ac.iit.TicketsReservationSystem.repository.ConsumerRepository;
import lk.ac.iit.TicketsReservationSystem.repository.VendorRepository;
import lk.ac.iit.TicketsReservationSystem.util.TicketsLoggers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.logging.Logger;


import static java.util.logging.Level.INFO;

/**
 * This class represents a pool of tickets for a reservation system. It manages the addition and purchase of tickets,
 * ensuring synchronization and logging of ticket operations.
 */
public class TicketPool {

    private final List<Integer> ticketsPool = Collections.synchronizedList(new ArrayList<Integer>());
    private final int maxCapacity;

    private final ConsumerRepository consumerRepository;
    private final VendorRepository vendorRepository;

    private int ticket = 1;

    public TicketPool(int maxCapacity, ConsumerRepository consumerRepository, VendorRepository vendorRepository) {
        this.maxCapacity = maxCapacity;
        this.consumerRepository = consumerRepository;
        this.vendorRepository = vendorRepository;
    }


    /**
     * Let the vendor add a new ticket to the pool. This method is synchronized to ensure thread safety.
     *
     * @param vendorID The ID of the vendor adding the ticket.
     * @throws Exception If an error occurs while adding the ticket.
     */
    public synchronized void addTickets(int vendorID) throws Exception {

        while (ticketsPool.size() >= maxCapacity){
            wait(); // wait until customer buys a ticket and notify
        }

        ticketsPool.add(ticket);
        VendorDAO vendorDAO = new VendorDAO(vendorID, ticket);
        vendorRepository.save(vendorDAO);
        TicketsLoggers.logInfo("Vendor " + vendorID + " Added ticket " + ticket + " TICKETPOOL - " + ticketsPool.size());
        ticket++;
        notifyAll();
    }


    /**
     * Let the consumer buy a ticket from the pool. This method is synchronized to ensure thread safety.
     *
     * @param consumerID The ID of the consumer buying the ticket.
     * @throws Exception If an error occurs while buying the ticket.
     */
    public synchronized void buyTickets(int consumerID) throws Exception {

        while (ticketsPool.isEmpty()){
            TicketsLoggers.logInfo("No tickets available");
            wait(); // wait until the vendor add a new ticket and notify
        }

        int i = ticketsPool.remove(0);
        ConsumerDAO consumerDAO = new ConsumerDAO(consumerID,i);
        consumerRepository.save(consumerDAO);
        TicketsLoggers.logInfo("Customer " + consumerID + " bought ticket " + i + " TICKETPOOL - " + ticketsPool.size());
        notifyAll();
    }

    /**
     * Returns the available tickets of the ticket pool.
     *
     * @return The number of tickets available in the pool.
     */
    public int getAvailablePortionTicketsPool() {
        return ticketsPool.size();
    }

}

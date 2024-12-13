package lk.ac.iit.TicketsReservationSystem.model;

import lk.ac.iit.TicketsReservationSystem.util.TicketsLoggers;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

public class Consumer implements Runnable{

    private TicketPool ticketPool;
    private final int consumerID;
    private final int customerRetrievalRate;

    public Consumer(TicketPool ticketPool, int consumerID, int customerRetrievalRate){
        this.ticketPool = ticketPool;
        this.consumerID = consumerID;
        this.customerRetrievalRate = customerRetrievalRate * 1000;
    }

    @Override
    public void run() {
        try{
            // customer buy tickets until respective thread is interrupted
            while(!Thread.currentThread().isInterrupted()){

                ticketPool.buyTickets(consumerID);

                // sleep the thread according to the user given time to automating purpose
                Thread.sleep(customerRetrievalRate);

            }

        } catch (Exception e) {
            // If anything goes wrong log sever exception
            TicketsLoggers.logSevere(e.getMessage());
        }
    }
}

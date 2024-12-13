package lk.ac.iit.TicketsReservationSystem.model;

import lk.ac.iit.TicketsReservationSystem.util.TicketsLoggers;

import static java.util.logging.Level.SEVERE;

public class Vendor implements Runnable{

    private TicketPool ticketPool;
    private int totalTicketsCount;
    private int vendorId;
    private int ticketReleaseRate;

    private int tickets = 1;

    public Vendor(TicketPool ticketPool, int totalTicketsCount, int vendorId, int ticketReleaseRate){
        this.ticketPool = ticketPool;
        this.totalTicketsCount = totalTicketsCount;
        this.vendorId = vendorId;
        this.ticketReleaseRate = ticketReleaseRate * 1000;
    }

    @Override
    public void run() {
        try {
            // Vendor add tickets until respective thread is interrupted
            while (!Thread.currentThread().isInterrupted()){
                if(tickets > totalTicketsCount){

                    // If tickets count is go beyond the user given count thread will be interrupted
                    Thread.currentThread().interrupt();

                } else {

                    ticketPool.addTickets(vendorId);

                    tickets++; // increment ticket count by one to keep track of the number of issued tickets

                    // sleep the thread according to the user given time to automating purpose
                    Thread.sleep(ticketReleaseRate);
                }
            }

        } catch (Exception e) {
            // If anything goes wrong log sever exception
            TicketsLoggers.logSevere(e.getMessage());
        }
    }
}

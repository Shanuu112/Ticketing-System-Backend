package lk.ac.iit.TicketsReservationSystem.service;

import lk.ac.iit.TicketsReservationSystem.model.Consumer;
import lk.ac.iit.TicketsReservationSystem.model.TicketPool;
import lk.ac.iit.TicketsReservationSystem.model.Vendor;
import lk.ac.iit.TicketsReservationSystem.repository.ConsumerRepository;
import lk.ac.iit.TicketsReservationSystem.repository.VendorRepository;
import lk.ac.iit.TicketsReservationSystem.util.TicketsLoggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

@Service
public class SystemServices {

    private TicketPool ticketPool;
    private List<Thread> vendors = new ArrayList<>();
    private List<Thread> consumers = new ArrayList<>();

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private VendorRepository vendorRepository;


    /**
     * This method starts the ticket reservation system with the predefined number of vendors and consumers.
     * It initializes the ticket pool, creates and starts threads for each vendor and consumer, and logs the system start.
     *
     * @param vendorCount The number of vendors to be created and started.
     * @param consumerCount The number of consumers to be created and started.
     * @param ticketReleaseRate The rate at which tickets are released by vendors.
     * @param customerRetrievalRate The rate at which customers retrieve tickets.
     * @param totalTicketsForVendors The total number of tickets to be released by each vendor.
     * @param maxTicketsCount The maximum number of tickets that can be in the pool.
     */
    public void startSystem( int vendorCount, int consumerCount, int ticketReleaseRate, int customerRetrievalRate, int totalTicketsForVendors, int maxTicketsCount){

        TicketsLoggers.logInfo("System Started with " + vendorCount + " Vendors and " + consumerCount + " Consumers.");
        ticketPool = new TicketPool(maxTicketsCount,consumerRepository,vendorRepository);

        for(int i = 1; i <= vendorCount; i++){
            Thread vendorThread = new Thread(new Vendor(ticketPool,totalTicketsForVendors,i,ticketReleaseRate));
            vendors.add(vendorThread);
            vendorThread.start();
        }


        for(int i = 1; i <= consumerCount; i++){
            Thread consumerThread = new Thread(new Consumer(ticketPool,i,customerRetrievalRate));
            consumers.add(consumerThread);
            consumerThread.start();
        }
    }


    /**
     * This method stops the ticket reservation system.
     * It interrupts all the threads of vendors and consumers, checks if they have been interrupted,
     * and logs the system stop.
     *
     * @return A ResponseEntity indicating the success or failure of the system stop.
     *         If the system is successfully stopped, it returns a ResponseEntity with status 200 (OK) and the message "System Successfully Stopped."
     *         If an error occurs when stopping the system, it returns a ResponseEntity with status 500 (INTERNAL_SERVER_ERROR) and the message "Error occurred when stopping the system."
     */
    public ResponseEntity<String> stopSystem(){
        boolean status = false;
        for(Thread vendor: vendors){
            vendor.interrupt();

            if(vendor.isInterrupted()){
                status = true;
            } else {
                status = false;
            }

        }

        for(Thread consumer: consumers){
            consumer.interrupt();

            if(consumer.isInterrupted()){
                status = true;
            } else {
                status = false;
            }

        }

        if(status){
            TicketsLoggers.logInfo("System Successfully Stopped.");
            return ResponseEntity.ok("System Successfully Stopped.");
        } else {
            TicketsLoggers.logSevere("Error occurred when stopping the system.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred when stopping the system.");
        }
    }


    /**
     * This method retrieves the available portion of tickets in the ticket pool.
     *
     * @return An integer representing the available portion of tickets in the ticket pool.
     *         The value returned will be between 0 and the maximum number of tickets (user defined) that can be in the pool.
     */
    public int getAvailablePortionTicketsPool(){
        if (ticketPool != null){
            return ticketPool.getAvailablePortionTicketsPool();
        }
        return 0;
    }
}

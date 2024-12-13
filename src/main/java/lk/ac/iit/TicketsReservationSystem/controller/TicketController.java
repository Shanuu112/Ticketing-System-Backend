package lk.ac.iit.TicketsReservationSystem.controller;

import lk.ac.iit.TicketsReservationSystem.model.LogsResponse;
import lk.ac.iit.TicketsReservationSystem.model.TicketPool;
import lk.ac.iit.TicketsReservationSystem.service.SystemServices;
import lk.ac.iit.TicketsReservationSystem.util.TicketsLoggers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/1/tickets")
@CrossOrigin(origins = "*") // Accepts all origins to avoid any conflicts
public class TicketController {

    private final SystemServices service;

    public TicketController(SystemServices service){
        this.service = service;
    }


    /**
     * Starts the ticket reservation system with the specified parameters.
     *
     * @param vendorCount The number of ticket vendors in the system
     * @param consumerCount The number of consumers (customers) in the system
     * @param ticketReleaseRate The rate at which tickets are released into the system
     * @param customerRetrievalRate The rate at which customers retrieve tickets
     * @param totalTicketsForVendors The total number of tickets allocated for vendors
     * @param maxTicketsCount The maximum number of tickets in the system
     * @return ResponseEntity containing a Map with the start status
     */
    @PostMapping("/start")
    public ResponseEntity<String> startProgramme(
                                                @RequestParam int vendorCount, @RequestParam int consumerCount,
                                                @RequestParam int ticketReleaseRate, @RequestParam int customerRetrievalRate,
                                                @RequestParam int totalTicketsForVendors, @RequestParam int maxTicketsCount){

        service.startSystem(vendorCount, consumerCount, ticketReleaseRate, customerRetrievalRate, totalTicketsForVendors, maxTicketsCount);
        return ResponseEntity.ok("Started");
    }


    /**
     * Stops the ticket reservation system and retrieves the stopped status.
     *
     * This endpoint is used to stop the ongoing ticket reservation processes.
     *
     * @return ResponseEntity<String> containing the status message of the stopped system.
     *         The status message provides information about the system's state after stopping.
     */
    @PostMapping("/stop")
    public ResponseEntity<String> getTickets(){
        ResponseEntity<String> status = service.stopSystem();
        return status;
    }



    /**
     * Retrieves the logs of the ticket reservation system and the available tickets in the pool.
     *
     * This endpoint is used to retrieve the logs and the current available tickets in the pool.
     * The logs are collected from the list object and are returned as a list of strings.
     * The available tickets in the pool are retrieved using the #SystemServices -> getAvailablePortionTicketsPool() method.
     *
     * @return ResponseEntity<LogsResponse> containing the available tickets in the pool and also the system logs.
     *         The LogsResponse object contains two fields:
     *         - availableForTicketPool: The number of available tickets in the pool.
     *         - logs: A list of strings representing the system logs.
     */
    @GetMapping("/logs")
    public ResponseEntity<LogsResponse> getLogs(){
        int availableForTicketPool = service.getAvailablePortionTicketsPool();
        List<String> logs = TicketsLoggers.getLogs().stream().collect(Collectors.toList());

        LogsResponse logResponse = new LogsResponse(availableForTicketPool, logs);
        return ResponseEntity.ok(logResponse);
    }
}

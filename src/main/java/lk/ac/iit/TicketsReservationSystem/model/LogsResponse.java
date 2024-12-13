package lk.ac.iit.TicketsReservationSystem.model;

import java.util.List;

public class LogsResponse {
    private int availableTickets;
    private List<String> logs;

    public LogsResponse(int availableTickets, List<String> logs) {
        this.availableTickets = availableTickets;
        this.logs = logs;
    }

    // Getters and Setters
    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}


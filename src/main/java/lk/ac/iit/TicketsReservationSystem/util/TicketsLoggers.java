package lk.ac.iit.TicketsReservationSystem.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketsLoggers {

    // Creating the Logger instance
    private static final Logger logger = Logger.getLogger(TicketsLoggers.class.getName());

    // To make the thread safety logging
    private static final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();


    // Log an info message
    public static void logInfo(String message) {
        logger.info(message);
        logs.add(message);
    }

    // Log a warning message
    public static void logWarning(String message) {
        logger.warning(message);
        logs.add("Warning : " + message);
    }

    // Log a severe error message
    public static void logSevere(String message) {
        logger.severe(message);
        logs.add("Severe : " + message);
    }

    public static ConcurrentLinkedQueue<String> getLogs() {
        return logs;
    }
}

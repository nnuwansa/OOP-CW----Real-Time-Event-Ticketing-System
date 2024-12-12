import java.util.Scanner;
public class TicketCustomer implements Runnable {
    private final TicketPool ticketPool;  // The ticket pool from which customers will retrieve tickets
    private final int customerRetrievalRate;

    // Constructor to initialize ticket pool and retrieval rate
    public TicketCustomer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // The run method that will be executed by the customer thread
    @Override
    public void run() {
        try {
            // Continuously try to retrieve tickets while the thread is not interrupted
            while (!Thread.currentThread().isInterrupted()) {
                // Try to retrieve a ticket, stop if no tickets are available
                if (!ticketPool.retrieveTicket() && ticketPool.getTicketCount() == 0) {
                    break;  // Exit the loop if no tickets are available
                }

                // Sleep for the defined rate before the next ticket retrieval attempt
                Thread.sleep(customerRetrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            // Handle interruption, print message and reset the interrupt flag
            System.out.println("Customer thread was interrupted.");
            Thread.currentThread().interrupt();
        }

        // Print a message when the customer thread is shutting down
        System.out.println("Customer thread is shutting down.");
    }
}

public class TicketPool {
    private int totalTickets = 0;  // Total number of tickets (maximum capacity)
    private int availableTickets = 0;  // Number of tickets currently available for retrieval

    // Constructor to initialize the total number of tickets
    public TicketPool(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    // Method for a vendor to add a ticket to the pool
    public synchronized boolean addTicket() {
        if (availableTickets < totalTickets) {
            availableTickets++;  // Increment available tickets count
            System.out.println("INFO: Ticket added by vendor. Total tickets: " + availableTickets);
            notifyAll();  // Notify any waiting customers that a ticket is available
            return true;
        }
        return false;  // Return false if no more tickets can be added
    }

    // Method for a customer to retrieve a ticket from the pool
    public synchronized boolean retrieveTicket() {
        while (availableTickets == 0) {  // If no tickets are available, the customer has to wait
            try {
                wait();  // Wait until a ticket becomes available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption if the waiting thread is interrupted
                return false;  // Exit if interrupted
            }
        }
        availableTickets--;  // Decrease the number of available tickets
        System.out.println("INFO: Ticket retrieved by customer. Tickets remaining: " + availableTickets);
        return true;  // Return true when a ticket is successfully retrieved
    }

    // Method to get the current count of available tickets
    public synchronized int getTicketCount() {
        return availableTickets;  // Return the number of available tickets
    }
}

public class TicketVendor implements Runnable {
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;

    public TicketVendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        try {
            int ticketsAdded = 0;
            while (ticketsAdded < totalTickets && !Thread.currentThread().isInterrupted()) {
                if (ticketPool.addTicket()) {
                    ticketsAdded++;
                }
                // Sleep for the defined rate before the next ticket release attempt
                Thread.sleep(ticketReleaseRate*1000);  // Release tickets at the defined rate
            }
        } catch (InterruptedException e) {
            System.out.println("TicketVendor thread was interrupted.");
            Thread.currentThread().interrupt();
        }
        System.out.println("TicketVendor thread is shutting down.");
    }
}

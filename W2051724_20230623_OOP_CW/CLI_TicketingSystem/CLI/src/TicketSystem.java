import java.io.IOException;
import java.util.Scanner;

public class TicketSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display greeting message
        System.out.println("Welcome to the Real-Time Ticket Management System!");
        System.out.print("Would you like to load the existing configuration from file? (yes/no): ");
        String loadConfig = scanner.nextLine().trim().toLowerCase();

        // Load configuration from file or create a new one
        SystemConfiguration config;
        if (loadConfig.equals("yes")) {
            config = SystemConfiguration.loadConfig();  // Load existing configuration
        } else {
            config = SystemConfiguration.newConfig(scanner);  // Create new configuration

            // Save the new configuration to a file
            try {
                config.saveToFile("configuration.txt");
                System.out.println("Configuration saved to configuration.txt");
            } catch (IOException e) {
                System.err.println("Failed to save configuration: " + e.getMessage());
            }
        }

        // Initialize the ticket pool with the total number of tickets
        TicketPool ticketPool = new TicketPool(config.totalTickets);

        // Create vendor and customer threads
        TicketVendor vendor = new TicketVendor(ticketPool, config.totalTickets, config.ticketReleaseRate);
        TicketCustomer customer = new TicketCustomer(ticketPool, config.customerRetrievalRate);

        // Start the vendor and customer threads
        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();

        // Display system is running message
        System.out.println("\nSystem is running. Tickets will be processed until all are sold or the system is interrupted.");

        // Monitor the system until tickets are sold or the system is interrupted
        try {
            while (true) {
                synchronized (ticketPool) {
                    // Exit the loop if no tickets remain and the vendor thread is done
                    if (ticketPool.getTicketCount() == 0 && !vendorThread.isAlive()) {
                        break;
                    }
                }
                Thread.sleep(1000); // Monitor every second
            }
        } catch (InterruptedException e) {
            System.out.println("System interrupted during monitoring.");
        } finally {
            // Interrupt threads to ensure graceful shutdown
            vendorThread.interrupt();
            customerThread.interrupt();

            // Wait for threads to finish
            try {
                vendorThread.join();
                customerThread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted during shutdown.");
            }
        }

        // Final messages to indicate the system has stopped
        System.out.println("Ticket System Stopped successfully.");
        System.out.println();
        System.out.println("Thank you for using the Real-Time Ticket Management System!");
    }
}

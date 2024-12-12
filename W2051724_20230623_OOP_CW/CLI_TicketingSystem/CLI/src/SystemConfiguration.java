import java.io.*;
import java.util.Scanner;

public class SystemConfiguration {
    public int totalTickets;
    public int maxTicketCapacity;
    public int ticketReleaseRate;
    public int customerRetrievalRate;

    // Constructor to initialize the configuration fields
    public SystemConfiguration(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Method to create a new configuration by asking user input
    public static SystemConfiguration newConfig(Scanner scanner) {
        System.out.println("Create a new configuration:");

        int totalTickets;
        int maxCapacity;

        // Loop to ensure total tickets don't exceed max capacity
        while (true) {
            totalTickets = promptForInteger(scanner, "Enter total tickets: ");
            maxCapacity = promptForInteger(scanner, "Enter max ticket capacity: ");

            // Validate that total tickets do not exceed max capacity
            if (totalTickets > maxCapacity) {
                System.out.println("ERROR: Total tickets cannot exceed maximum ticket capacity. Please re-enter values.");
            } else {
                break;
            }
        }

        // Prompt for ticket release and customer retrieval rates
        int ticketReleaseRate = promptForInteger(scanner, "Enter ticket release rate : ");
        int customerRetrievalRate = promptForInteger(scanner, "Enter customer retrieval rate : ");

        // Return a new SystemConfiguration object with user inputs
        return new SystemConfiguration(totalTickets, maxCapacity, ticketReleaseRate, customerRetrievalRate);
    }

    // Helper method to prompt and validate integer input from user
    public static int promptForInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next(); // Read input as a string
            try {
                return Integer.parseInt(input); // Try to convert the string to an integer
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method to load configuration from a file
    public static SystemConfiguration loadConfig() {
        System.out.println("Loading configuration from file...");

        try (BufferedReader reader = new BufferedReader(new FileReader("configuration.txt"))) {
            // Read and parse configuration values from file
            int totalTickets = Integer.parseInt(reader.readLine().split(": ")[1]);
            int maxTicketCapacity = Integer.parseInt(reader.readLine().split(": ")[1]);
            int ticketReleaseRate = Integer.parseInt(reader.readLine().split(": ")[1]);
            int customerRetrievalRate = Integer.parseInt(reader.readLine().split(": ")[1]);

            // Create and return a SystemConfiguration object with loaded values
            SystemConfiguration config = new SystemConfiguration(
                    totalTickets,
                    maxTicketCapacity,
                    ticketReleaseRate,
                    customerRetrievalRate
            );

            // Print the loaded configuration
            System.out.println("Configuration loaded successfully:");
            System.out.println("Total Tickets: " + totalTickets + ", Max Ticket Capacity: " + maxTicketCapacity + ", Ticket Release Rate: " + ticketReleaseRate + ", Customer Retrieval Rate: " + customerRetrievalRate);

            return config;
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error reading configuration file or invalid format.");
            return null; // Return null if there's an error
        }
    }

    // Method to save the current configuration to a text file
    public void saveToFile(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write configuration values to the file
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Max Ticket Capacity: " + maxTicketCapacity + "\n");
            writer.write("Ticket Release Rate: " + ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate: " + customerRetrievalRate + "\n");
        }
    }
}

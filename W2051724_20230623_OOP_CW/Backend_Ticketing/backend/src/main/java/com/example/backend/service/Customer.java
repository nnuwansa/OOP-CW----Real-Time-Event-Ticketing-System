package com.example.backend.service;


import com.example.backend.model.TicketPool;
import com.example.backend.model.TicketSale;
import com.example.backend.repository.TicketSaleRepository;
import org.springframework.stereotype.Component;

@Component
public class Customer implements Runnable {

    private final TicketPool ticketPool;
    private final TicketSaleRepository ticketSaleRepository;
    private static int customerCounter = 1; // Counter for generating customer IDs

    public Customer(TicketPool ticketPool, TicketSaleRepository ticketSaleRepository) {
        this.ticketPool = ticketPool;
        this.ticketSaleRepository = ticketSaleRepository;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String ticket = ticketPool.removeTicket();
                if (ticket == null && ticketPool.areAllTicketsConsumed()) {
                    System.out.println("INFO: All tickets have been retrieved by customers. Shutting down.");
                    break;
                }

                // Generate dynamic customer ID in the format 001, 002, etc.
                String customerId = String.format("%03d", customerCounter++);  // Format with leading zeros
                System.out.println("INFO: Ticket " + ticket + " retrieved by Customer " + customerId);

                // Save ticket sale to the database
                TicketSale sale = new TicketSale();
                sale.setTicketId(ticket);
                sale.setCustomerId(customerId);  // Set the formatted customer ID
                sale.setSaleTime(java.time.LocalDateTime.now());
                ticketSaleRepository.save(sale);

                // Simulate customer retrieval delay
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Customer thread interrupted.");
        }
    }
}





package com.example.backend.model;

import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class TicketPool {
    private final Queue<String> tickets = new LinkedList<>();
    private volatile boolean allTicketsGenerated = false;


    //Adds a ticket to the queue and logs the ticket count.
    public synchronized void addTicket(String ticket) throws InterruptedException {
        tickets.add(ticket);
        System.out.println("INFO: Ticket added by vendor. Total tickets: " + tickets.size());
        notifyAll();
    }


    //Simulates ticket retrieval by customers.
    public synchronized String removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            if (allTicketsGenerated) {
                return null; // No more tickets to process
            }
            wait();
        }

        String ticket = tickets.poll();
        System.out.println("INFO: Ticket retrieved by customer. Tickets remaining: " + tickets.size());
        notifyAll();
        return ticket;
    }


    //Marks all tickets as generated, allowing threads to stop waiting.
    public synchronized void markAllTicketsGenerated() {
        allTicketsGenerated = true;
        notifyAll();
    }


    //Checks if all tickets have been consumed and generation has ended.
    public synchronized boolean areAllTicketsConsumed() {
        return tickets.isEmpty() && allTicketsGenerated;
    }

    //Dynamically fetch the current ticket count from the queue size.
    public synchronized int getTicketCount() {
        return tickets.size();
    }


}


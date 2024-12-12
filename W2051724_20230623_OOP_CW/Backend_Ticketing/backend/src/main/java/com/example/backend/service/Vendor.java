package com.example.backend.service;

import com.example.backend.model.TicketPool;
import org.springframework.stereotype.Component;

@Component
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private int ticketCounter = 1;
    private int totalTicketsToGenerate;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Method to dynamically update the totalTicketsToGenerate
    public void setTotalTicketsToGenerate(int totalTickets) {
        this.totalTicketsToGenerate = totalTickets;
    }

    @Override
    public void run() {
        try {
            while (ticketCounter <= totalTicketsToGenerate) {
                String ticket = "Ticket_" + ticketCounter++;
                ticketPool.addTicket(ticket);
                Thread.sleep(500); // Simulate delay
            }
            System.out.println("INFO: All tickets have been added.");
            ticketPool.markAllTicketsGenerated();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Vendor interrupted.");
        }
    }
}




package com.example.backend.model;

import jakarta.persistence.*;
@Entity
@Table(name = "ticket_config")
public class TicketConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets = 10; // Default value set here

    @Column(name = "ticket_release_rate", nullable = false)
    private int ticketReleaseRate = 5;  //default

    @Column(name = "customer_retrieval_rate", nullable = false)
    private int customerRetrievalRate = 3; // default

    @Column(name = "max_ticket_capacity", nullable = false)
    private int maxTicketCapacity = 100; //  default

    // Getters and setters
    public Long getId() {
        return id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}


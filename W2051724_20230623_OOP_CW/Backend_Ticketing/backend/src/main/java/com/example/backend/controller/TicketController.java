package com.example.backend.controller;

import com.example.backend.model.TicketConfig;
import com.example.backend.model.TicketPool;
import com.example.backend.model.TicketSale;
import com.example.backend.service.Customer;
import com.example.backend.service.TicketConfigService;
import com.example.backend.service.Vendor;
import com.example.backend.repository.TicketSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketPool ticketPool;
    private final Vendor vendor;
    private final Customer customer;
    private final ExecutorService executorService;
    private final TicketConfigService ticketConfigService;
    private final TicketSaleRepository ticketSaleRepository;

    @Autowired
    public TicketController(
            TicketPool ticketPool,
            Vendor vendor,
            Customer customer,
            TicketConfigService ticketConfigService,
            TicketSaleRepository ticketSaleRepository
    ) {
        this.ticketPool = ticketPool;
        this.vendor = vendor;
        this.customer = customer;
        this.executorService = Executors.newFixedThreadPool(2);
        this.ticketConfigService = ticketConfigService;
        this.ticketSaleRepository = ticketSaleRepository;
    }

    /**
     * Start the ticket simulation by saving the configuration and running threads.
     */
    @PostMapping("/start")
    public ResponseEntity<String> startSimulation(@RequestBody TicketConfig ticketConfig) {
        // Save configuration details to the database
        TicketConfig savedConfig = ticketConfigService.saveConfig(ticketConfig);

        // Set the total tickets dynamically
        vendor.setTotalTicketsToGenerate(savedConfig.getTotalTickets());

        // Start vendor and customer threads
        executorService.submit(vendor);
        executorService.submit(customer);

        return ResponseEntity.ok("Ticket simulation started and configuration saved!");
    }


    /**
     * Get the current count of tickets in the pool.
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getTicketCount() {
        int ticketCount = ticketPool.getTicketCount();
        return ResponseEntity.ok(ticketCount);
    }

    /**
     * Get all ticket sales details.
     */
    @GetMapping("/sales")
    public ResponseEntity<List<TicketSale>> getAllSales() {
        List<TicketSale> sales = ticketSaleRepository.findAll();
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sales);
    }

    /**
     * Stop the system and shutdown all operations.
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        executorService.shutdown();
        return ResponseEntity.ok("System is shutting down.");
    }
}




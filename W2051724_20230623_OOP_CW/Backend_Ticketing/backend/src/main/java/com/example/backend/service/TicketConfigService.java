package com.example.backend.service;

import com.example.backend.model.TicketConfig;
import com.example.backend.repository.TicketConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TicketConfigService {

    private final TicketConfigRepository repository;

    @Autowired
    public TicketConfigService(TicketConfigRepository repository) {
        this.repository = repository;
    }

    public TicketConfig getConfig() {
        return repository.findAll().stream().findFirst().orElse(null);
    }

    public TicketConfig saveConfig(TicketConfig ticketConfig) {
        return repository.save(ticketConfig);
    }
}




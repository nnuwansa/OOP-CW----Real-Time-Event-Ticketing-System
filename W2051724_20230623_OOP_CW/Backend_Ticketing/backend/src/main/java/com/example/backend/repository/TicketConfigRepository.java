package com.example.backend.repository;

import com.example.backend.model.TicketConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketConfigRepository extends JpaRepository<TicketConfig, Long> {

}

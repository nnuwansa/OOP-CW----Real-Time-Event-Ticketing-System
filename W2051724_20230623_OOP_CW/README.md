# Ticket Management System

## Project Overview

The Ticket Management System is a simulation of a ticket generation and retrieval process, enabling concurrent operations between vendors and customers. Vendors generate tickets and add them to a shared pool, while customers retrieve tickets from this pool. The system ensures smooth communication between the backend and frontend, allowing users to efficiently manage ticket operations.

---

## Technology Used

### Backend

- **Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Concurrency**: Java ExecutorService for handling concurrent operations
- **Database**: MySQL

### Frontend

- **Language**: JavaScript
- **Framework**: React
- **Styling**: Bootstrap
- **State Management**: React Context API

---

## Key Components

### Backend

1. **TicketController**: Handles API requests for managing ticket operations.
   - **Endpoints**:
     - `/api/tickets/start` (POST): Starts the ticket simulation by saving configuration and running vendor and customer threads.
     - `/api/tickets/shutdown` (POST): Shuts down the simulation threads.
     - `/api/tickets/count` (GET): Retrieves the current count of tickets in the pool.
     - `/api/tickets/sales` (GET): Fetches all ticket sales details.
     - `/api/tickets/stop` (POST): Stops the system and initiates shutdown of all operations.
2. **TicketPool**: A thread-safe shared resource where tickets are stored and accessed.
3. **Vendor**: Simulates ticket generation and adds tickets to the pool.
4. **Customer**: Simulates ticket retrieval by customers from the pool.
5. **TicketConfigService**: Handles saving and retrieving ticket configuration details.
6. **TicketSaleRepository**: Manages database interactions for ticket sales.

### Frontend

1. **TicketForm**: A React component for users to start or stop the simulation and manage tickets.
2. **TicketList**: Displays the list of retrieved tickets and their details.
3. **API Integration**: Uses Axios to communicate with the backend.

---

## Setup Instructions

### Prerequisites

#### Backend

- **Java 17+**
- **Maven**
- **MySQL Database**

#### Frontend

- **Node.js 18+**
- **Bootstrap** (included via npm or CDN)

---

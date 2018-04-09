package org.softuni.maintenancemanager.tickets.model.repositories;

import org.softuni.maintenancemanager.tickets.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

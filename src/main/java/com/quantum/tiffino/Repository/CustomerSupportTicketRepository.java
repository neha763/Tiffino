package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.CustomerSupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSupportTicketRepository extends JpaRepository<CustomerSupportTicket,Long> {
}

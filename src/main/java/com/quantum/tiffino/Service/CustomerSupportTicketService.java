package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.CustomerSupportTicket;

import java.util.List;

public interface CustomerSupportTicketService {
    CustomerSupportTicket createTicket(CustomerSupportTicket ticket);
    CustomerSupportTicket getTicketById(long ticketId);
    List<CustomerSupportTicket> getAllTickets();
    CustomerSupportTicket updateTicketStatus(long ticketId, String status);

    boolean deleteTicket(long ticketId);
}

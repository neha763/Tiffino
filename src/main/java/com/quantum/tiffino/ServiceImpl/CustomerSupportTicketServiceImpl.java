package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.CustomerSupportTicket;
import com.quantum.tiffino.Exception.TicketNotFoundException;
import com.quantum.tiffino.Repository.CustomerSupportTicketRepository;
import com.quantum.tiffino.Service.CustomerSupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerSupportTicketServiceImpl implements CustomerSupportTicketService {

    private final CustomerSupportTicketRepository ticketRepository;

    @Autowired
    public CustomerSupportTicketServiceImpl(CustomerSupportTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CustomerSupportTicket createTicket(CustomerSupportTicket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public CustomerSupportTicket getTicketById(long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found"));
    }

    @Override
    public List<CustomerSupportTicket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public CustomerSupportTicket updateTicketStatus(long ticketId, String status) {
        CustomerSupportTicket ticket = getTicketById(ticketId);
        try {
            ticket.setStatus(CustomerSupportTicket.Status.valueOf(status.toUpperCase()));
            return ticketRepository.save(ticket);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }
}

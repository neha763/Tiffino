package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.CustomerSupportTicket;
import com.quantum.tiffino.Exception.TicketNotFoundException;
import com.quantum.tiffino.Repository.CustomerSupportTicketRepository;
import com.quantum.tiffino.Service.CustomerSupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        ticket.setCreatedAt(LocalDate.now()); // Set createdAt
        return ticketRepository.save(ticket);
    }
@Override
    public CustomerSupportTicket getTicketById(long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
    }
@Override
    public List<CustomerSupportTicket> getAllTickets() {
        return ticketRepository.findAll();
    }
@Override
    public CustomerSupportTicket updateTicketStatus(long ticketId, String status) {
        CustomerSupportTicket ticket = getTicketById(ticketId);
        ticket.setStatus(CustomerSupportTicket.Status.valueOf(status));

        if (status.equalsIgnoreCase("RESOLVED")) {
            ticket.setResolvedAt(LocalDate.now()); // Set resolvedAt if resolved
        }

        return ticketRepository.save(ticket);
    }

    public boolean deleteTicket(long ticketId) {
        if (ticketRepository.existsById(ticketId)) {
            ticketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }
}


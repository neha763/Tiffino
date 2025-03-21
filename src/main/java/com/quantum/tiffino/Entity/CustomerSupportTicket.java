package com.quantum.tiffino.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer_support_ticket")
public class CustomerSupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    private String customerName;
    private String issueType;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdAt;
    private LocalDate resolvedAt;

    public CustomerSupportTicket() {
        this.createdAt = LocalDate.now(); // Default createdAt to today
        this.status = Status.OPEN; // Default status to OPEN
    }

    // Constructor
    public CustomerSupportTicket(String customerName, String issueType, String description) {
        this.customerName = customerName;
        this.issueType = issueType;
        this.description = description;
        this.status = Status.OPEN;
        this.createdAt = LocalDate.now();
        this.resolvedAt = null;
    }

    // Getters and Setters
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;

        if (status == Status.RESOLVED && this.resolvedAt == null) {
            this.resolvedAt = LocalDate.now(); // Set resolvedAt only if it's not already set
        }
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDate resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    // toString Method for easy debugging
    @Override
    public String toString() {
        return "Ticket ID: " + ticketId +
                ", Customer: " + customerName +
                ", Issue Type: " + issueType +
                ", Status: " + status +
                ", Created At: " + createdAt +
                (resolvedAt != null ? ", Resolved At: " + resolvedAt : "");
    }

    // Enum for ticket status
    public enum Status {
        OPEN,
        RESOLVED,
        CLOSED
    }
}

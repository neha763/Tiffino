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

    // Enum for status
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdAt;
    private LocalDate resolvedAt;

    // Constructor
    public CustomerSupportTicket(long ticketId, String customerName, String issueType, String description) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.issueType = issueType;
        this.description = description;
        this.status = Status.OPEN;  // Default status set to OPEN
        this.createdAt = LocalDate.now();  // Set created date to the current date
        this.resolvedAt = null;  // Resolved date is null initially
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
        if (status == Status.RESOLVED) {
            this.resolvedAt = LocalDate.now();  // Set resolvedAt to current date if status is resolved
        }
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getResolvedAt() {
        return resolvedAt;
    }

    // toString Method for easy printing
    @Override
    public String toString() {
        return "Ticket ID: " + ticketId +
                ", Customer: " + customerName +
                ", Issue Type: " + issueType +
                ", Status: " + status +
                ", Created At: " + createdAt +
                (resolvedAt != null ? ", Resolved At: " + resolvedAt : "");
    }

    // Enum for status
    public enum Status {
        OPEN,
        RESOLVED,
        CLOSED
    }
}

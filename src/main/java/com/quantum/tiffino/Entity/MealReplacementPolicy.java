package com.quantum.tiffino.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MealReplacementPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;
    private String orderId;
    private String customerName;
    private String customerEmail;
    private String reasonForRefund;


    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate requestDate;
    private LocalDate processedDate;

    // Constructor
    public MealReplacementPolicy(Long policyId, String orderId, String customerName,
                                 String customerEmail, String reasonForRefund,
                                 Status status, LocalDate requestDate, LocalDate processedDate) {
        this.policyId = policyId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.reasonForRefund = reasonForRefund;
        this.status = status;
        this.requestDate = requestDate;
        this.processedDate = processedDate;
    }


    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getReasonForRefund() {
        return reasonForRefund;
    }

    public void setReasonForRefund(String reasonForRefund) {
        this.reasonForRefund = reasonForRefund;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDate processedDate) {
        this.processedDate = processedDate;
    }


    @Override
    public String toString() {
        return "MealReplacementPolicy{" +
                "policyId=" + policyId +
                ", orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", reasonForRefund='" + reasonForRefund + '\'' +
                ", status=" + status +
                ", requestDate=" + requestDate +
                ", processedDate=" + processedDate +
                '}';
    }


}

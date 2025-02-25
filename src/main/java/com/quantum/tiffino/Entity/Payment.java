package com.quantum.tiffino.Entity;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Payment {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private double amount;
        private Date paymentDate;
        private String paymentMethod;

        @Enumerated(EnumType.STRING)
        private PaymentStatus paymentStatus;

        @OneToMany(mappedBy = "payment")
        private List<Subscription> subscriptions;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        public Date getPaymentDate() {
                return paymentDate;
        }

        public void setPaymentDate(Date paymentDate) {
                this.paymentDate = paymentDate;
        }

        public String getPaymentMethod() {
                return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
        }

        public List<Subscription> getSubscriptions() {
                return subscriptions;
        }

        public void setSubscriptions(List<Subscription> subscriptions) {
                this.subscriptions = subscriptions;
        }

        public PaymentStatus getPaymentStatus() {
                return paymentStatus;
        }

        public void setPaymentStatus(PaymentStatus paymentStatus) {
                this.paymentStatus = paymentStatus;
        }
}



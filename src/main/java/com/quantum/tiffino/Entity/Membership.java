package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", nullable = false)
    private MembershipType membershipType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public Membership(){}

    public Membership(Long id, User user, MembershipType membershipType, LocalDateTime expirationDate) {
        this.id = id;
        this.user = user;
        this.membershipType = membershipType;

        if (expirationDate == null) {
            this.expirationDate = LocalDateTime.now().plusMonths(membershipType.getExpiryMonth());
        } else {
            this.expirationDate = expirationDate.plusMonths(membershipType.getExpiryMonth());
        }

    }

    public double getDiscount() {
        return membershipType.getDiscountPercentage();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}


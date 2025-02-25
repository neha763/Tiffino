package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Membership;
import com.quantum.tiffino.Entity.MembershipType;

import java.util.List;

public interface MembershipService {
    Membership assignMembership(Long user_id,Membership membership);
    Membership updateMembership(Long user_id, MembershipType membershipType);
    void removeMembership(Long user_id);

    List<Membership> getAllMemberships();

    Membership getMembershipByUserId(Long user_id);
}



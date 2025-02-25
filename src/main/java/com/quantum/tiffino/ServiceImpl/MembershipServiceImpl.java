package com.quantum.tiffino.ServiceImpl;


import com.quantum.tiffino.Entity.Membership;
import com.quantum.tiffino.Entity.MembershipType;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantum.tiffino.Repository.MembershipRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    UserRepository userRepository;

//    @Override
//    public Membership assignMembership(Long userId,Membership membership) {
//    User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not Found:"+userId));
////        Membership membership = new Membership();
////        membership.setUser(user);
// //       membership.setMembershipType(membershipType);
////        membership.setExpirationDate(OffsetDateTime.now().plusMonths(12));
////        return membershipRepository.save(membership);
//      return   membershipRepository.findByUser(user);
//    }

public Membership assignMembership(Long userId, Membership membership) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    // Set expiration date based on membership type if not provided
    if (membership.getExpirationDate() == null) {
        membership.setExpirationDate(LocalDateTime.now().plusMonths(membership.getMembershipType().getExpiryMonth()));
    }

    membership.setUser(user);
    return membershipRepository.save(membership);
}


    @Override
    public Membership updateMembership(Long user_id, MembershipType membershipType) {
        Membership membership = membershipRepository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        membership.setMembershipType(membershipType);
        return membershipRepository.save(membership);
    }

    @Override
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Override
    public Membership getMembershipByUserId(Long user_id) {
        return membershipRepository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("Membership not found for User ID: " + user_id));
    }

    @Override
    public void removeMembership(Long user_id) {
        Membership membership = membershipRepository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("Membership not found for User ID: " + user_id));

        membershipRepository.delete(membership);
    }
}

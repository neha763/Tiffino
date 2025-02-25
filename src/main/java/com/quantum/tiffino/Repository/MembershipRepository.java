package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Membership;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
    Optional<Membership> findByUserId(Long user_id);

    Membership findByUser(User user);
}

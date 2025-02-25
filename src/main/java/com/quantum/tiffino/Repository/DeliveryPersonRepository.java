package com.quantum.tiffino.Repository;


import com.quantum.tiffino.Entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    // You can add custom queries here if needed.
}

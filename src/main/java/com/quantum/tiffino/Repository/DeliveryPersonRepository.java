package com.quantum.tiffino.Repository;


import com.quantum.tiffino.Entity.DeliveryPerson;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    Optional<DeliveryPerson> findByEmail(String email);

    Optional<DeliveryPerson> findByPhoneNumber(String phoneNumber);

    List<DeliveryPerson> findByAvailable(boolean available);

}

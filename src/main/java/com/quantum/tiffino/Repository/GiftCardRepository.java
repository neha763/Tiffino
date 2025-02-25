package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard,Long> {
}

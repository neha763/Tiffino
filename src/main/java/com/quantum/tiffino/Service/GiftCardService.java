package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.GiftCard;

import java.util.List;
import java.util.Optional;

public interface GiftCardService {
    GiftCard UpdateGiftCardById(Long id, GiftCard giftCard);

    Optional<GiftCard> getGiftCard(Long id);

    GiftCard saveGiftCard(GiftCard giftCard);

    void deleteGiftCard(Long id);

    GiftCard redeemGiftCard(Long id);

    GiftCard expireGiftCard(Long id);

    GiftCard createGiftCard(GiftCard giftCard);

    List<GiftCard> getAllGiftCards();

    Optional <GiftCard>getGiftCardById(Long id);
}


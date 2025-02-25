package com.quantum.tiffino.ServiceImpl;
import com.quantum.tiffino.Entity.GiftCard;
import com.quantum.tiffino.Entity.GiftCardStatus;
import com.quantum.tiffino.Exception.GiftCardNotFoundException;
import com.quantum.tiffino.Repository.GiftCardRepository;
import com.quantum.tiffino.Service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    private GiftCardRepository giftCardRepository;


    public List<GiftCard> getAllGift() {
        return giftCardRepository.findAll();
    }



    @Override
    public GiftCard UpdateGiftCardById(Long id, GiftCard giftCard) {
        Optional<GiftCard> giftCardForUpdate = giftCardRepository.findById(id);
        if (giftCardForUpdate.isPresent()) {
            GiftCard updatedGiftCard = giftCardForUpdate.get();
            updatedGiftCard.setAmount(giftCard.getAmount());
            updatedGiftCard.setSenderName(giftCard.getSenderName());
            updatedGiftCard.setRecipientName(giftCard.getRecipientName());
            updatedGiftCard.setPersonalizedMessage(giftCard.getPersonalizedMessage());
            updatedGiftCard.setEmail(giftCard.getEmail());
            updatedGiftCard.setExpirationDate(giftCard.getExpirationDate());
            updatedGiftCard.setIssueDate(giftCard.getIssueDate());
            updatedGiftCard.setStatus(giftCard.getStatus());

            return giftCardRepository.save(updatedGiftCard);
        } else {
            return null;
        }
    }


    @Override
    public Optional<GiftCard> getGiftCard(Long id) {
        return giftCardRepository.findById(id);
    }

    @Override
    public GiftCard saveGiftCard(GiftCard giftCard) {
        if (giftCard == null) {
            throw new IllegalArgumentException(" Gift card can not be null");
        }
        return giftCardRepository.save(giftCard);
    }

    @Override
    public void deleteGiftCard(Long id)

    {
        Optional<GiftCard> exitingGiftCard = giftCardRepository.findById(id);
        if (exitingGiftCard.isPresent()) {
            giftCardRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("GiftCard for user ID " + id + " not found.");
        }

    }

    @Override
    public GiftCard redeemGiftCard(Long id) {
        Optional<GiftCard> optionalGiftCard = giftCardRepository.findById(id);
        if(optionalGiftCard.isPresent()){
            GiftCard giftCard = optionalGiftCard.get();
            if (giftCard.getStatus()== GiftCardStatus.ACTIVE){
                giftCard.setStatus(GiftCardStatus.REDEEMED);
                return giftCardRepository.save(giftCard);
            }
            else
            {
                throw new IllegalStateException("Gift card is not active or has Redeemd");
            }}
        else
        {
            throw new IllegalArgumentException("gift card with id"+id+"not found");
        }
    }


    @Override
    public GiftCard expireGiftCard(Long id) {
        Optional<GiftCard> optionalGiftCard = giftCardRepository.findById(id);
        if (optionalGiftCard.isPresent()){
            GiftCard giftCard = optionalGiftCard.get();
            if(giftCard.getStatus()==GiftCardStatus.ACTIVE){
                giftCard.setStatus(GiftCardStatus.EXPIRED);
                return giftCardRepository.save(giftCard);
            }
            else {
                throw new IllegalStateException("Gift card is not active or has Expire");
            }}
        else {
            throw new IllegalArgumentException("gift card with id"+id+"not found");
        }
    }



    @Override
    public GiftCard createGiftCard(GiftCard giftCard) {

        return giftCardRepository.save(giftCard);
    }




    @Override
    public List<GiftCard> getAllGiftCards() {
        return giftCardRepository.findAll();
    }

    @Override
    public Optional <GiftCard>getGiftCardById(Long id){
        Optional<GiftCard> giftCard=giftCardRepository.findById(id);
        if ((giftCard.isEmpty())) {
            throw new GiftCardNotFoundException("gift card not found with id:" + id);

        }
        return giftCard;
    }


}









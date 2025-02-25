package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.GiftCard;
import com.quantum.tiffino.Service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/giftcard")
@CrossOrigin("*")
public class GiftCardController {


        @Autowired
        private GiftCardService giftCardService;


        @GetMapping("/user/{id}")
        public ResponseEntity<GiftCard> getUser(@PathVariable Long id) {
            Optional<GiftCard> giftCard = giftCardService.getGiftCard(id);
            return giftCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }


        @GetMapping("/all")
        public ResponseEntity<?> getAllGiftCards() {
            return ResponseEntity.ok(giftCardService.getAllGiftCards());
        }


        @PostMapping("/")
        public ResponseEntity<GiftCard> createGiftCard(@RequestBody GiftCard giftCard) {
            if (giftCard == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            GiftCard savedGiftCard = giftCardService.createGiftCard(giftCard);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGiftCard);
        }


        @PutMapping("/user/{id}")
        public ResponseEntity<GiftCard> updateGiftCard(@PathVariable Long id, @RequestBody GiftCard giftCard) {
            if (!id.equals(giftCard.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            GiftCard updatedGiftCard = giftCardService.UpdateGiftCardById(id, giftCard);
            return updatedGiftCard != null ? ResponseEntity.ok(updatedGiftCard) : ResponseEntity.notFound().build();
        }


        @DeleteMapping("/user/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            giftCardService.deleteGiftCard(id);
            return ResponseEntity.noContent().build();
        }
    }




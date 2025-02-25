package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Membership;
import com.quantum.tiffino.Entity.MembershipType;
import com.quantum.tiffino.Service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin("*")
public class MembershipController {
    @Autowired
    private MembershipService membershipService;

    @PostMapping("/assign/{user_id}")
    public Membership assignMembership(@PathVariable Long user_id,@RequestBody Membership membership){
//                                                       @RequestBody Map<String, String> request) {
//        String membershipTypeStr = request.get("membershipType");
//        MembershipType membershipType = MembershipType.valueOf(membershipTypeStr);
//        Membership membership = membershipService.assignMembership(user_id, membershipType);
 //       return ResponseEntity.ok(membership);
        return   membershipService.assignMembership(user_id,membership);
    }


    @PutMapping("/update/{user_id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long user_id,
                                                       @RequestBody Map<String, String> request) {
        String membershipTypeStr = request.get("membershipType");
        MembershipType membershipType = MembershipType.valueOf(membershipTypeStr);
        Membership updatedMembership = membershipService.updateMembership(user_id, membershipType);
        return ResponseEntity.ok(updatedMembership);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        List<Membership> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Membership> getMembershipByUserId(@PathVariable Long user_id) {
        Membership membership = membershipService.getMembershipByUserId(user_id);
        return ResponseEntity.ok(membership);
    }

    @DeleteMapping("/remove/{user_id}")
    public ResponseEntity<String> deleteMembershipByUserId(@PathVariable Long user_id) {
        membershipService.removeMembership(user_id);
        return ResponseEntity.ok("Membership deleted successfully for User ID: " + user_id);
    }
}

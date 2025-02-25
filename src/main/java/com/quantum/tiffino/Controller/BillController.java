package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Bill;
import com.quantum.tiffino.Entity.Order;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Service.BillService;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin("*")
public class BillController {

    private final BillService billService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public BillController(BillService billService, UserRepository userRepository, OrderRepository orderRepository) {
        this.billService = billService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {

        Long userId = bill.getUser().getId();


        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        bill.setUser(userOptional.get());


        Long orderId = bill.getOrder().getId();
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        bill.setOrder(orderOptional.get());


        Bill createdBill = billService.createBill(bill);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.getAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }
}

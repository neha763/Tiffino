package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Bill;
import com.quantum.tiffino.Exception.BillNotFoundException;
import com.quantum.tiffino.Repository.BillRepository;
import com.quantum.tiffino.Service.BillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill getBillById(Long id) {
        Optional<Bill> bill = billRepository.findById(id);
        if (bill.isPresent()) {
            return bill.get();
        } else {
            throw new BillNotFoundException(id);
        }
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
}

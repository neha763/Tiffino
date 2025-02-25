package com.quantum.tiffino.Service;
import com.quantum.tiffino.Entity.Bill;
import java.util.List;

public interface BillService {
    Bill createBill(Bill bill);

    Bill getBillById(Long id);

    List<Bill> getAllBills();
}

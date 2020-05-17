package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.BillRepository;
import edu.utn.utnPhones.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public void add(Bill bill) {
        billRepository.save(bill);
    }
}

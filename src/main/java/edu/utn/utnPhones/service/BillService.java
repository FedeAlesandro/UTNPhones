package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.BillRepository;
import edu.utn.utnPhones.model.Bill;
import edu.utn.utnPhones.projections.BillsByDateRange;
import edu.utn.utnPhones.projections.CallsByDateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<BillsByDateRange> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){
        return billRepository.getBillsByDateRange(idUser, date1, date2);
    }
}

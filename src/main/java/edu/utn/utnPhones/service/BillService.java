package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.BillRepository;
import edu.utn.utnPhones.model.Bill;
import edu.utn.utnPhones.projections.BillsForUsers;
import edu.utn.utnPhones.projections.BillsWithoutPhoneCalls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public List<BillsWithoutPhoneCalls> getBills() {
        return billRepository.getBills();
    }

    public void add(Bill bill) {
        billRepository.save(bill);
    }

    public List<BillsForUsers> getBillsByUser(Integer idUser){
        return billRepository.getBillsByUser(idUser);
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){
        return billRepository.getBillsByDateRange(idUser, date1, date2);
    }

    public List<Bill> getBillsByPhoneLine(Integer idPhoneLine) {
        return billRepository.findByIdPhoneLine(idPhoneLine);
    }

    public Bill getBillById(Integer idBill) {

        return Optional.ofNullable(billRepository.findById(idBill))
                .get()
                .orElseThrow(RuntimeException::new); //Aca va una excepcion creada por nosotros
    }
}

package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.repositories.BillRepository;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_BILL;
import static edu.utn.utnPhones.utils.Constants.BILL_NOT_FOUND_PHONE_LINE;

@Service
public class BillService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository, UserRepository userRepository){
        this.userRepository = userRepository;
        this.billRepository = billRepository;
    }

    public List<BillsWithoutPhoneCalls> getBills() {
        return billRepository.getBills();
    }

    public Bill add(Bill bill) {
        return billRepository.save(bill);
    }

    public List<BillsForUsers> getBillsByUser(Integer idUser){
        userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException("This user doesn't exist")); // todo pedirle constante a fabio

        return billRepository.getBillsByUser(idUser);
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){
        return billRepository.getBillsByDateRange(idUser, date1, date2);
    }

    public List<Bill> getBillsByPhoneLine(Integer idPhoneLine) {
        return billRepository.findByIdPhoneLine(idPhoneLine)
                .orElseThrow(() -> new NotFoundException(BILL_NOT_FOUND_PHONE_LINE));
    }

    public Bill getBillById(Integer idBill) {
        return billRepository.findById(idBill)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BILL));
    }
}

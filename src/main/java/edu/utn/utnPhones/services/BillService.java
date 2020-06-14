package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.repositories.BillRepository;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_BILL;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public List<BillsWithoutPhoneCalls> getBills() {

        return billRepository.getBills();
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){

        return billRepository.getBillsByDateRange(idUser, date1, date2);
    }

    public Bill getBillById(Integer idBill) {

        return billRepository.findById(idBill)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BILL));
    }
}

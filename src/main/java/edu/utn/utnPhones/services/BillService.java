package edu.utn.utnPhones.services;

import edu.utn.utnPhones.exceptions.NotFoundException;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.enums.BillStatus;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static edu.utn.utnPhones.utils.Constants.NOT_FOUND_BILL;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public List<BillsWithoutPhoneCalls> getBills() {

        return billRepository.getBills();
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, Date date1, Date date2){

        if (date2 == null)
            date2 = Date.from(Instant.now());
        else{
            Date aux;
            if(date2.before(date1)){
                aux = date2;
                date2 = date1;
                date1 = aux;
            }
        }

        return billRepository.getBillsByDateRange(idUser, date1, date2);
    }

    public Bill getBillById(Integer idBill) {

        return billRepository.findById(idBill)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BILL));
    }

    public Bill payBill(Integer idBill) {

        Bill bill = billRepository.findById(idBill)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BILL));

        if (bill.getState() == BillStatus.payed)
            throw new NotFoundException(NOT_FOUND_BILL);

        bill.setState(BillStatus.payed);

        return billRepository.save(bill);
    }
}

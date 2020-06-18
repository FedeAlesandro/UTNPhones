package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    public List<BillsWithoutPhoneCalls> getBills(){

        return billService.getBills();
    }

    public List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){

        return billService.getBillsByDateRange(idUser, date1, date2);
    }

    public Bill getBillById(Integer idBill){

        return billService.getBillById(idBill);
    }

    public Bill payBill(Integer idBill) {

        return billService.payBill(idBill);
    }
}

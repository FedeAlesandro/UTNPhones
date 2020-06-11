package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.dtos.BillByIdDTO;
import edu.utn.utnPhones.models.dtos.BillsByIdPhoneCallDto;
import edu.utn.utnPhones.models.dtos.BillsByPhoneLine;
import edu.utn.utnPhones.models.dtos.BillsPhoneCallDto;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    //Get general sin lista de llamadas
    public List<BillsWithoutPhoneCalls> getBills(){

        return billService.getBills();
    }

    //Lista de facturas por rango de fecha para el usuario
    public List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2){

        return billService.getBillsByDateRange(idUser, date1, date2);
    }

    public Bill getBillById(Integer idBill){

        return billService.getBillById(idBill);
    }
}

package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    //Get general sin lista de llamadas
    @GetMapping("/")
    public List<BillsWithoutPhoneCalls> getBills(){
        return billService.getBills();
    }

    //Lista de facturas para el usuario
    @GetMapping("/{idUser}/user/")
    public List<BillsForUsers> getBillsByUser(@PathVariable(value = "idUser") Integer idUser){
        return billService.getBillsByUser(idUser);
    }

    //Lista de facturas por rango de fecha para el usuario
    @GetMapping("/{idUser}/daterange/")
    public List<BillsForUsers> getBillsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                   @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                   @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){
        return billService.getBillsByDateRange(idUser, date1, date2);
    }

    //Lista de facturas por linea (muestra lista de llamadas por factura)
    @GetMapping("/{idPhoneLine}/phoneline/")
    public List<Bill> getBillsByPhoneLine(@PathVariable(value = "idPhoneLine") Integer idPhoneLine){
        return billService.getBillsByPhoneLine(idPhoneLine);
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid Bill bill){
        billService.add(bill);
    }

    @GetMapping("/{idBill}/")
    public Bill getBillById(@PathVariable(value = "idBill") Integer idBill){
        return billService.getBillById(idBill);
    }
}

package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.Bill;
import edu.utn.utnPhones.projections.BillsByDateRange;
import edu.utn.utnPhones.service.BillService;
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

    @GetMapping("/")
    public List<Bill> getAll(){
        return billService.getAll();
    }

    @GetMapping("/{idUser}/")
    public List<BillsByDateRange> getBillsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                      @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                      @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){
        return billService.getBillsByDateRange(idUser, date1, date2);
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid Bill bill){
        billService.add(bill);
    }
}

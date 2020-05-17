package edu.utn.utnPhones.controller;

import edu.utn.utnPhones.model.Bill;
import edu.utn.utnPhones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    @GetMapping
    public List<Bill> getAll(){
        return billService.getAll();
    }

    @PostMapping
    public void add(@RequestBody @Valid Bill bill){
        billService.add(bill);
    }
}

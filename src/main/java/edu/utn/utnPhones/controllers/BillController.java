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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    //Get general sin lista de llamadas
    @GetMapping("/")
    public ResponseEntity<List<BillsWithoutPhoneCalls>> getBills(){
        List<BillsWithoutPhoneCalls>bills = billService.getBills();
        if(bills.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(bills);
    }

    //Lista de facturas para el usuario
    @GetMapping("/{idUser}/user/")
    public ResponseEntity<List<BillsForUsers>> getBillsByUser(@PathVariable(value = "idUser") Integer idUser){
        return ResponseEntity.ok(billService.getBillsByUser(idUser));
    }

    //Lista de facturas por rango de fecha para el usuario
    @GetMapping("/{idUser}/daterange/")
    public ResponseEntity<List<BillsForUsers>> getBillsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                   @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                   @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){

        List<BillsForUsers>bills = billService.getBillsByDateRange(idUser, date1, date2);
        if(bills.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(billService.getBillsByDateRange(idUser, date1, date2));
    }

    //Lista de facturas por linea (muestra lista de llamadas por factura)
    @GetMapping("/{idPhoneLine}/phoneline/")
    public ResponseEntity<List<BillsByPhoneLine>> getBillsByPhoneLine(@PathVariable(value = "idPhoneLine") Integer idPhoneLine){

        List<Bill>bills = billService.getBillsByPhoneLine(idPhoneLine);
        List<BillsByPhoneLine>billsByPhoneLines = new ArrayList<>();
        List<BillsPhoneCallDto> calls = new ArrayList<>();

        for(Bill bill : bills){
            calls = bill.getCalls()
            .stream()
            .map(BillsPhoneCallDto::new)
                    .collect(Collectors.toList());
            billsByPhoneLines.add(new BillsByPhoneLine(bill, calls));
        }

        return ResponseEntity.ok(billsByPhoneLines);
    }

    @GetMapping("/{idBill}/")
    public ResponseEntity<?> getBillById(@PathVariable(value = "idBill") Integer idBill){
        Bill bill = billService.getBillById(idBill);

        List<BillsByIdPhoneCallDto> calls = bill.getCalls()
                .stream()
                .map(BillsByIdPhoneCallDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BillByIdDTO(bill, calls));
    }
}

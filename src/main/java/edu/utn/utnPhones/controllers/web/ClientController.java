package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final PhoneCallController phoneCallController;

    private final BillController billController;

    @Autowired
    public ClientController(PhoneCallController phoneCallController, BillController billController){
        this.phoneCallController = phoneCallController;
        this.billController = billController;
    }

    //todo No se tiene que pasar el idUser, porque sino el usuario logueado puede ver las llamadas de cualquiera. Hay que hacer un RequestHeader
    @GetMapping("/{idUser}/calls")
    public ResponseEntity<List<CallsByDateRange>> getCallsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                                      @RequestParam(value = "date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                                                      @RequestParam(value = "date2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){

        List<CallsByDateRange> callsList = phoneCallController.getCallsByDateRange(idUser, date1, date2);

        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    //todo Aca lo mismo, no pasar el idUser. Despues hay que fusionar los dos metodos de llamada
    @GetMapping("/{idUser}/destinations")
    public ResponseEntity<List<MostCalledDestination>> getMostCalledDestinations(@PathVariable(value = "idUser") Integer idUser) {

        List<MostCalledDestination> callsList = phoneCallController.getMostCalledDestinations(idUser);

        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @GetMapping("/{idUser}/bills")
    public ResponseEntity<List<BillsForUsers>> getBillsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                                   @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                                   @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){
        List<BillsForUsers> bills = billController.getBillsByDateRange(idUser, date1, date2);

        return bills.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bills);
    }
}

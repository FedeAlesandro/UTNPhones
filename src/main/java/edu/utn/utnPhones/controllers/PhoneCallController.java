package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoResponse;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.services.PhoneCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/calls")
public class PhoneCallController {

    private PhoneCallService phoneCallService;

    @Autowired
    public PhoneCallController(PhoneCallService phoneCallService){
        this.phoneCallService = phoneCallService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PhoneCall>> getAll(){
        List<PhoneCall> callsList = phoneCallService.getAll();
        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @GetMapping("/{idUser}/dateRange")
    public ResponseEntity<List<CallsByDateRange>> getCallsByDateRange(@PathVariable(value = "idUser") Integer idUser,
                                                      @RequestParam(value = "date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                                      @RequestParam(value = "date2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){
        List<CallsByDateRange> callsList = phoneCallService.getCallsByDateRange(idUser, date1, date2);
        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @GetMapping("/{idUser}/destinations")
    public ResponseEntity<List<MostCalledDestination>> getMostCalledDestinations(@PathVariable(value = "idUser") Integer idUser) {
        List<MostCalledDestination> callsList = phoneCallService.getMostCalledDestinations(idUser);
        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @GetMapping("/{idUser}/")
    public ResponseEntity<List<CallsByUser>> getByUser(@PathVariable(value = "idUser") Integer idUser){
        List<CallsByUser> callsList = phoneCallService.getByUser(idUser);
        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @PostMapping("/") //
    public ResponseEntity<PhoneCallDtoResponse> add(@RequestBody @Valid PhoneCallDtoAdd phoneCall){
        PhoneCall addedCall = phoneCallService.add(phoneCall);
        return ResponseEntity.created(URI.create("Localhost:8081")).body(PhoneCallDtoResponse.builder()
                    .id(addedCall.getId())
                    .originPhoneNumber(addedCall.getOriginPhoneNumber())
                    .destinationPhoneNumber(addedCall.getDestinationPhoneNumber())
                    .duration(addedCall.getDuration())
                    .date(addedCall.getDate())
                    .totalCost(addedCall.getTotalCost())
                    .totalPrice(addedCall.getTotalPrice())
                    .build());
    }

}

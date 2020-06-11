package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.dtos.responses.PhoneCallDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/infrastructure")
public class InfrastructureController {

    private final PhoneCallController phoneCallController;

    @Autowired
    public InfrastructureController(PhoneCallController phoneCallController){

        this.phoneCallController = phoneCallController;
    }

    @PostMapping("/")
    public ResponseEntity<PhoneCallDtoResponse> add(@RequestBody @Valid PhoneCallDtoAdd phoneCall){

        PhoneCall addedCall = phoneCallController.add(phoneCall);

        return ResponseEntity.status(HttpStatus.CREATED).body(PhoneCallDtoResponse.builder()
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

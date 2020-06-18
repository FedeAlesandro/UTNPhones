package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.dtos.responses.PhoneCallDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/infrastructure")
public class InfrastructureController {

    private final PhoneCallController phoneCallController;

    @PostMapping
    public ResponseEntity<PhoneCallDtoResponse> addPhoneCall(@RequestBody @Valid PhoneCallDtoAdd phoneCall) {

        PhoneCall addedCall = phoneCallController.add(phoneCall);

        return ResponseEntity.status(HttpStatus.CREATED).body(PhoneCallDtoResponse.fromPhoneCall(addedCall));
    }
}

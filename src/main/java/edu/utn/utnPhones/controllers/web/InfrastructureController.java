package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/infrastructure")
public class InfrastructureController {

    private final PhoneCallController phoneCallController;

    @PostMapping
    public ResponseEntity<URI> addPhoneCall(@RequestBody @Valid PhoneCallDtoAdd phoneCall) {

        return ResponseEntity.created(phoneCallController.add(phoneCall)).build();
    }
}

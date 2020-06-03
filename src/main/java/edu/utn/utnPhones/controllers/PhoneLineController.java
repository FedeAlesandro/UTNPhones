package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.dtos.BillsPhoneLineDto;
import edu.utn.utnPhones.models.dtos.PhoneCallPhoneLineDto;
import edu.utn.utnPhones.models.dtos.PhoneLineAdd;
import edu.utn.utnPhones.models.dtos.PhoneLineByIdDto;
import edu.utn.utnPhones.models.dtos.PhoneLineResponse;
import edu.utn.utnPhones.models.dtos.PhoneLineUpdate;
import edu.utn.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phoneLines")
public class PhoneLineController {

    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PhoneLineResponse>> getAll(){
        List<PhoneLine> phoneLines = phoneLineService.getAll();

        if(phoneLines.isEmpty())
            return ResponseEntity.noContent().build();
        else{
            return ResponseEntity.ok(phoneLineService.getAll()
                    .stream()
                    .map(PhoneLineResponse::new)
                    .collect(Collectors.toList()));
        }
    }

    @GetMapping("/all/")
    public ResponseEntity<List<PhoneLineResponse>> getAllWithoutFilter(){
        List<PhoneLine> phoneLines = phoneLineService.getAllWithoutFilter();

        if(phoneLines.isEmpty())
            return ResponseEntity.noContent().build();
        else{
            return ResponseEntity.ok(phoneLineService.getAllWithoutFilter()
                    .stream()
                    .map(PhoneLineResponse::new)
                    .collect(Collectors.toList()));
        }
    }
    @GetMapping("/{id}/")
    public ResponseEntity<PhoneLineByIdDto> getById(@PathVariable(value = "id") Integer id){
        PhoneLine phoneLine = phoneLineService.getById(id);

        List<BillsPhoneLineDto> bills = phoneLine.getBills()
                .stream()
                .map(BillsPhoneLineDto::new)
                .collect(Collectors.toList());

        List<PhoneCallPhoneLineDto> originPhoneCalls = phoneLine.getOriginPhoneCalls()
                .stream()
                .map(PhoneCallPhoneLineDto::new)
                .collect(Collectors.toList());

        List<PhoneCallPhoneLineDto> destinationPhoneCalls = phoneLine.getDestinationPhoneCalls()
                .stream()
                .map(PhoneCallPhoneLineDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PhoneLineByIdDto(phoneLine, bills, originPhoneCalls, destinationPhoneCalls));
    }

    @PostMapping("/")
    public ResponseEntity<PhoneLineResponse> add(@RequestBody @Valid PhoneLineAdd phoneLine){
        return ResponseEntity.status(HttpStatus.CREATED).body(new PhoneLineResponse(phoneLineService.add(phoneLine)));
    }

    @PutMapping("/{id}/")
    public ResponseEntity<PhoneLineResponse> update(@PathVariable(value = "id") Integer id, @RequestBody @Valid PhoneLineUpdate phoneLineUpdate){
        return ResponseEntity.ok(new PhoneLineResponse(phoneLineService.update(id, phoneLineUpdate)));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> remove(@PathVariable(value = "id") Integer id){
        phoneLineService.remove(id);
        return ResponseEntity.ok().build();
    }
}

package edu.utn.utnPhones.controllers;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.dtos.PhoneLineAdd;
import edu.utn.utnPhones.models.dtos.PhoneLineUpdate;
import edu.utn.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PhoneLineController {

    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    // todo preguntarle a Pablo si está bien no agregarlas
    /*@GetMapping("/")
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
    }*/

    public PhoneLine add(PhoneLineAdd phoneLine){

        return phoneLineService.add(phoneLine);
    }

    public PhoneLine update(Integer id, PhoneLineUpdate phoneLineUpdate){

        return phoneLineService.update(id, phoneLineUpdate);
    }

    public void remove(Integer id){
        phoneLineService.remove(id);
    }
}

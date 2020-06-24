package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.controllers.PhoneLineController;
import edu.utn.utnPhones.controllers.TariffController;
import edu.utn.utnPhones.controllers.UserController;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.Tariff;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.dtos.responses.BillDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.PhoneCallDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.PhoneLineDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.TariffDtoResponse;
import edu.utn.utnPhones.models.dtos.responses.UserDtoResponse;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserController userController;

    private final PhoneCallController phoneCallController;

    private final PhoneLineController phoneLineController;

    private final BillController billController;

    private final TariffController tariffController;

    @GetMapping("/clients")
    public ResponseEntity<?> getClients(@RequestParam(value = "userName", required = false) String userName){

        if (userName == null){

            List<ClientsWithoutPassword> clientsList = userController.getClients();

            return clientsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientsList) : ResponseEntity.ok().body(clientsList);
        }

        User user = userController.getClient(userName);

        return ResponseEntity.ok(UserDtoResponse.fromUser(user));
    }

    @PostMapping("/clients")
    public ResponseEntity<URI> addUser(@RequestBody @Valid UserDtoAdd user){

        return ResponseEntity.created(RestUtils.getLocation(userController.add(user))).build();
    }

    @DeleteMapping("/clients/{idUser}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "idUser") Integer idUser){

        userController.remove(idUser);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/clients/{idUser}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPut user){

        User updatedUser = userController.update(idUser, user);

        return ResponseEntity.ok(UserDtoResponse.fromUser(updatedUser));
    }

    @PatchMapping("/clients/{idUser}")
    public ResponseEntity<UserDtoResponse> partialUpdateUser(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPatch user){

        User updatedUser = userController.partialUpdate(idUser, user);

        return ResponseEntity.ok(UserDtoResponse.fromUser(updatedUser));
    }

    @GetMapping("/users/{idUser}/calls")
    public ResponseEntity<List<CallsByUser>> getCallsByUser(@PathVariable(value = "idUser") Integer idUser){

        List<CallsByUser> callsList = phoneCallController.getByUser(idUser);

        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok(callsList);
    }

    @PostMapping("/phone-lines")
    public ResponseEntity<URI> addPhoneLine(@RequestBody @Valid PhoneLineDtoAdd phoneLine){

        return ResponseEntity.created(RestUtils.getLocation(phoneLineController.add(phoneLine))).build();
    }

    @PutMapping("/phone-lines/{id}")
    public ResponseEntity<PhoneLineDtoResponse> updatePhoneLine(@PathVariable(value = "id") Integer id, @RequestBody @Valid PhoneLineDtoUpdate phoneLineUpdate){

        return ResponseEntity.ok(PhoneLineDtoResponse.fromPhoneLine(phoneLineController.update(id, phoneLineUpdate)));
    }

    @DeleteMapping("/phone-lines/{id}")
    public ResponseEntity<Void> removePhoneLine(@PathVariable(value = "id") Integer id){

        phoneLineController.remove(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/phone-lines")
    public ResponseEntity<List<PhoneLineDtoResponse>> getPhoneLines(@RequestParam(value = "userName", required = false) String userName){

        List<PhoneLineDtoResponse> phoneLines;
        if (userName == null) {

            phoneLines = phoneLineController.getAll()
                    .stream()
                    .map(PhoneLineDtoResponse::fromPhoneLine)
                    .collect(Collectors.toList());


        } else {

            phoneLines = phoneLineController.getByUserName(userName)
                    .stream()
                    .map(PhoneLineDtoResponse::fromPhoneLine)
                    .collect(Collectors.toList());
        }

        return phoneLines.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(phoneLines) : ResponseEntity.ok(phoneLines);
    }

    @GetMapping("/bills")
    public ResponseEntity<?> getBills(@RequestParam(value = "idBill", required = false) Integer idBill){

        if (idBill != null) {

            Bill bill = billController.getBillById(idBill);

            List<PhoneCallDtoResponse> calls = bill.getCalls()
                    .stream()
                    .map(PhoneCallDtoResponse::fromPhoneCall)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(BillDtoResponse.fromBill(bill, calls));
        }

        List<BillsWithoutPhoneCalls> bills = billController.getBills();

        return bills.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bills);
    }

    @PatchMapping("/bills/{idBill}")
    public ResponseEntity<BillDtoResponse> payBill(@PathVariable("idBill") Integer idBill){

        Bill bill = billController.payBill(idBill);
        List<PhoneCallDtoResponse> calls = bill.getCalls()
                .stream()
                .map(PhoneCallDtoResponse::fromPhoneCall)
                .collect(Collectors.toList());

        return ResponseEntity.ok(BillDtoResponse.fromBill(bill, calls));
    }

    @GetMapping("/tariffs")
    public ResponseEntity<?> getTariffs(@RequestParam(value = "idTariff", required = false) Integer idTariff){

        if (idTariff == null) {

            List<TariffDtoResponse> tariffs = tariffController.getAll().stream()
                    .map(TariffDtoResponse::fromTariff)
                    .collect(Collectors.toList());

            return tariffs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tariffs);
        }

        Tariff tariff = tariffController.getById(idTariff);

        return ResponseEntity.ok(TariffDtoResponse.fromTariff(tariff));
    }

}

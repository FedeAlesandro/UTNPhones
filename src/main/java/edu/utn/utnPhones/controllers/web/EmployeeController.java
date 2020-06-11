package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.controllers.PhoneLineController;
import edu.utn.utnPhones.controllers.TariffController;
import edu.utn.utnPhones.controllers.UserController;
import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.BillByIdDTO;
import edu.utn.utnPhones.models.dtos.BillsByIdPhoneCallDto;
import edu.utn.utnPhones.models.dtos.CityDto;
import edu.utn.utnPhones.models.dtos.PhoneLineAdd;
import edu.utn.utnPhones.models.dtos.responses.PhoneLineResponse;
import edu.utn.utnPhones.models.dtos.PhoneLineUpdate;
import edu.utn.utnPhones.models.dtos.ProvinceDto;
import edu.utn.utnPhones.models.dtos.responses.TariffResponseDto;
import edu.utn.utnPhones.models.dtos.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.UserDtoPut;
import edu.utn.utnPhones.models.dtos.responses.UserDtoResponse;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final UserController userController;

    private final PhoneCallController phoneCallController;

    private final PhoneLineController phoneLineController;

    private final BillController billController;

    private final TariffController tariffController;

    @Autowired
    public EmployeeController(UserController userController,
                              PhoneCallController phoneCallController,
                              PhoneLineController phoneLineController,
                              BillController billController,
                              TariffController tariffController){
        this.userController = userController;
        this.phoneCallController = phoneCallController;
        this.phoneLineController = phoneLineController;
        this.billController = billController;
        this.tariffController = tariffController;
    }

    //Listar clientes no eliminados
    @GetMapping("/clients/")
    public ResponseEntity<List<ClientsWithoutPassword>> getClients(){

        List<ClientsWithoutPassword> clientsList = userController.getClients();

        return clientsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientsList) : ResponseEntity.ok().body(clientsList);
    }

    @GetMapping("/clients/{userName}/")
    public ResponseEntity<UserDtoResponse> getClient(@PathVariable(value = "userName") String userName){

        User user = userController.getClient(userName);
        ProvinceDto provinceDto = ProvinceDto.builder().name(user.getCity().getProvince().getName()).build();
        CityDto cityDto = CityDto.builder().name(user.getCity().getName()).areaCode(user.getCity().getAreaCode()).province(provinceDto).build();

        return ResponseEntity.ok().body(UserDtoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .dni(user.getDni())
                .userName(userName)
                .city(cityDto)
                .build());
    }

    @PostMapping("/users/")
    public ResponseEntity<UserDtoResponse> add(@RequestBody @Valid UserDtoAdd user){

        User addedUser = userController.add(user);
        ProvinceDto provinceDto = ProvinceDto.builder().name(addedUser.getCity().getProvince().getName()).build();
        CityDto cityDto = CityDto.builder().name(addedUser.getCity().getName()).areaCode(addedUser.getCity().getAreaCode()).province(provinceDto).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoResponse.builder()
                .id(addedUser.getId())
                .name(addedUser.getName())
                .lastName(addedUser.getLastName())
                .dni(addedUser.getDni())
                .userName(addedUser.getUserName())
                .city(cityDto).build());
    }

    @DeleteMapping("/users/{idUser}/")
    public ResponseEntity<?> remove(@PathVariable(value = "idUser") Integer idUser){

        userController.remove(idUser);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{idUser}/")
    public ResponseEntity<UserDtoResponse> update(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPut user){

        User updateUser = userController.update(idUser, user);
        ProvinceDto provinceDto = ProvinceDto.builder().name(updateUser.getCity().getProvince().getName()).build();
        CityDto cityDto = CityDto.builder().name(updateUser.getCity().getName()).areaCode(updateUser.getCity().getAreaCode()).province(provinceDto).build();

        return ResponseEntity.ok().body(UserDtoResponse.builder()
                .id(idUser)
                .name(updateUser.getName())
                .lastName(updateUser.getLastName())
                .dni(updateUser.getDni())
                .userName(updateUser.getUserName())
                .city(cityDto)
                .build());
    }

    @PatchMapping("/users/{idUser}/")
    public ResponseEntity<UserDtoResponse> partialUpdate(@PathVariable(value = "idUser") Integer idUser, @RequestBody @Valid UserDtoPatch user){

        User updateUser = userController.partialUpdate(idUser, user);
        ProvinceDto provinceDto = ProvinceDto.builder().name(updateUser.getCity().getProvince().getName()).build();
        CityDto cityDto = CityDto.builder().name(updateUser.getCity().getName()).areaCode(updateUser.getCity().getAreaCode()).province(provinceDto).build();

        return ResponseEntity.ok().body(UserDtoResponse.builder()
                .id(idUser)
                .name(updateUser.getName())
                .lastName(updateUser.getLastName())
                .dni(updateUser.getDni())
                .userName(updateUser.getUserName())
                .city(cityDto)
                .build());
    }

    @GetMapping("/users/{idUser}/calls/")
    public ResponseEntity<List<CallsByUser>> getByUser(@PathVariable(value = "idUser") Integer idUser){

        List<CallsByUser> callsList = phoneCallController.getByUser(idUser);

        return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
    }

    @PostMapping("/phone-lines/")
    public ResponseEntity<PhoneLineResponse> add(@RequestBody @Valid PhoneLineAdd phoneLine){

        return ResponseEntity.status(HttpStatus.CREATED).body(new PhoneLineResponse(phoneLineController.add(phoneLine)));
    }

    @PutMapping("/phone-lines/{id}/")
    public ResponseEntity<PhoneLineResponse> update(@PathVariable(value = "id") Integer id, @RequestBody @Valid PhoneLineUpdate phoneLineUpdate){

        return ResponseEntity.ok(new PhoneLineResponse(phoneLineController.update(id, phoneLineUpdate)));
    }

    @DeleteMapping("/phone-lines/{id}/")
    public ResponseEntity<Void> removePhoneLine(@PathVariable(value = "id") Integer id){
        phoneLineController.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bills/{idBill}/")
    public ResponseEntity<BillByIdDTO> getBillById(@PathVariable(value = "idBill") Integer idBill){
        Bill bill = billController.getBillById(idBill);

        List<BillsByIdPhoneCallDto> calls = bill.getCalls()
                .stream()
                .map(BillsByIdPhoneCallDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BillByIdDTO(bill, calls));
    }

    //Get general sin lista de llamadas
    @GetMapping("/bills/")
    public ResponseEntity<List<BillsWithoutPhoneCalls>> getBills(){
        List<BillsWithoutPhoneCalls>bills = billController.getBills();

        return bills.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bills);
    }

    @GetMapping("/tariffs/")
    public ResponseEntity<List<TariffResponseDto>> getAll(){
        List<TariffResponseDto>tariffs = tariffController.getAll().stream()
                .map(TariffResponseDto::new)
                .collect(Collectors.toList());

        return tariffs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tariffs);
    }

    @GetMapping("/tariffs/{id}/")
    public TariffResponseDto getById(@PathVariable(value = "id") Integer id){

        return new TariffResponseDto(tariffController.getById(id));
    }
}

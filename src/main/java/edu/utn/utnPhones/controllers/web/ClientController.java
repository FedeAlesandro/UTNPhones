package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.BillController;
import edu.utn.utnPhones.controllers.PhoneCallController;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import edu.utn.utnPhones.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    private final PhoneCallController phoneCallController;

    private final BillController billController;

    private final SessionManager sessionManager;

    @GetMapping("/calls")
    public ResponseEntity<?> getCalls(@RequestHeader("Authorization") String token,
                                      @RequestParam(value = "date1", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                      @RequestParam(value = "date2", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){

        User currentUser = sessionManager.getCurrentUser(token);

        if (date1 == null && date2 == null) {

            List<MostCalledDestination> callsList = phoneCallController.getMostCalledDestinations(currentUser.getId());

            return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
        } else {

            if (date1 != null && date2 != null) {

                List<CallsByDateRange> callsList = phoneCallController.getCallsByDateRange(currentUser.getId(), date1, date2);

                return callsList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(callsList) : ResponseEntity.ok().body(callsList);
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @GetMapping("/bills")
    public ResponseEntity<List<BillsForUsers>> getBills(@RequestHeader("Authorization") String token,
                                                                   @RequestParam(value = "date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                                   @RequestParam(value = "date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2){

        User currentUser = sessionManager.getCurrentUser(token);

        List<BillsForUsers> bills = billController.getBillsByDateRange(currentUser.getId(), date1, date2);

        return bills.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bills);
    }
}

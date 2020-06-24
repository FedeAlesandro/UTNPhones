package edu.utn.parcial.controllers;

import edu.utn.parcial.models.BillsForUsers;
import edu.utn.parcial.services.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired
    RestTemplateService restTemplateService;

    @GetMapping
    public ResponseEntity<BillsForUsers[]> getBills(@RequestHeader(value = "Authorization") String token,
                                                    @RequestParam(value = "date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                                    @RequestParam(value = "date2", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){

        return restTemplateService.getBills(token, date1, date2);
    }
}

package edu.utn.utnPhones.controllers.web;

import edu.utn.utnPhones.controllers.UserController;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.LoginRequestDto;
import edu.utn.utnPhones.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final UserController userController;

    private final SessionManager sessionManager;

    @PostMapping("login/")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto loginRequestDto){

        User user = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPwd());
        String token = sessionManager.createSession(user);

        return ResponseEntity.ok().headers(createHeaders(token)).build();
    }

    @PostMapping("logout/")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {

        sessionManager.removeSession(token);

        return ResponseEntity.ok().build();
    }
    private HttpHeaders createHeaders(String token) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);

        return responseHeaders;
    }
}

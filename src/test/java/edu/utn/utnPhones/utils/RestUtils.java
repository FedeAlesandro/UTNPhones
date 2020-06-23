package edu.utn.utnPhones.utils;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.User;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RestUtils {

    public static URI getLocation(PhoneCall phoneCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPhoneCall}")
                .buildAndExpand(phoneCall.getId())
                .toUri();
    }

    public static URI getLocation(PhoneLine phoneLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPhoneLine}")
                .buildAndExpand(phoneLine.getId())
                .toUri();
    }

    public static URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idUser}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}

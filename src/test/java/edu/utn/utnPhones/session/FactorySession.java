package edu.utn.utnPhones.session;

import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.Province;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.enums.LineType;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.models.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public interface FactorySession {

    default Province createProvince() {

        return Province.builder()
                .id(1)
                .name("Buenos Aires")
                .build();
    }

    default City createCity() {

        return City.builder()
                .id(1)
                .areaCode("223")
                .name("Mar del Plata")
                .province(createProvince())
                .build();
    }

    default PhoneLine createPhoneLine(){

        return PhoneLine.builder()
                .user(User.builder()
                        .id(1)
                        .build())
                .state(PhoneLineStatus.register)
                .lineType(LineType.mobile)
                .phoneNumber("2235860225")
                .build();
    }

    default User createUser(){

        List<PhoneLine> phoneLines = new ArrayList<>();
        phoneLines.add(createPhoneLine());

        return User.builder()
                .dni("38054312")
                .userName("fabiolaguna")
                .pwd("12345678")
                .userType(UserType.client)
                .city(createCity())
                .phoneLines(phoneLines)
                .removed(false)
                .build();
    }
}

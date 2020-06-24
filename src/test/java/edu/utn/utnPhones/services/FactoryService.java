package edu.utn.utnPhones.services;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.City;
import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.Province;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneCallDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPatch;
import edu.utn.utnPhones.models.dtos.requests.UserDtoPut;
import edu.utn.utnPhones.models.enums.BillStatus;
import edu.utn.utnPhones.models.enums.LineType;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import edu.utn.utnPhones.models.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public interface FactoryService {

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

    default PhoneLine createPhoneLineRemoved(){

        return PhoneLine.builder()
                .user(User.builder()
                        .id(1)
                        .build())
                .state(PhoneLineStatus.removed)
                .lineType(LineType.mobile)
                .phoneNumber("2235860225")
                .build();
    }

    default PhoneLineDtoAdd createPhoneLineDtoAdd(){

        return PhoneLineDtoAdd.builder()
                .user(User.builder()
                        .id(1)
                        .build())
                .lineType(LineType.mobile)
                .phoneNumber("2235860225")
                .build();
    }

    default User createUser(String userName){

        return User.builder()
                .id(2)
                .userName(userName)
                .userType(UserType.client)
                .removed(false)
                .build();
    }

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

    default PhoneLineDtoUpdate createPhoneLineDtoUpdate(){

        return PhoneLineDtoUpdate.builder()
                .user(User.builder()
                        .id(1)
                        .build())
                .state(PhoneLineStatus.register)
                .lineType(LineType.home)
                .phoneNumber("2235860225")
                .build();
    }

    default Bill createBill(){

        return Bill.builder()
                .state(BillStatus.sent)
                .build();
    }

    default Bill createBillPayed(){

        return Bill.builder()
                .state(BillStatus.payed)
                .build();
    }

    default UserDtoAdd createUserDtoAdd(){

      return UserDtoAdd.builder()
                .areaCode("223")
                .city("Mar del Plata")
                .dni("38054312")
                .province("Buenos Aires")
                .pwd("12345678")
                .userName("fabiolaguna")
                .userType("client")
                .build();
    }

    default UserDtoPut createUserDtoPut(){

        return UserDtoPut.builder()
                .areaCode("223")
                .city("Mar del Plata")
                .dni("40000000")
                .province("Buenos Aires")
                .pwd("12345678")
                .userName("hola1234")
                .build();
    }

    default UserDtoPatch createUserDtoPatch(){

        return UserDtoPatch.builder()
                .areaCode("223")
                .city("Mar del Plata")
                .dni("40000000")
                .province("Buenos Aires")
                .pwd("12345678")
                .userName("hola1234")
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

    default PhoneCallDtoAdd createPhoneCallAddDto(){

        return PhoneCallDtoAdd.builder()
                .destinationPhoneNumber("225654123")
                .originPhoneNumber("112345678")
                .duration(123)
                .build();
    }

    default PhoneCall createPhoneCall(){

        return PhoneCall.fromDto(createPhoneCallAddDto());
    }
}

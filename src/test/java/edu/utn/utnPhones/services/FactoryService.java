package edu.utn.utnPhones.services;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.enums.LineType;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;

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
}

package edu.utn.utnPhones.services;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoAdd;
import edu.utn.utnPhones.models.dtos.requests.PhoneLineDtoUpdate;
import edu.utn.utnPhones.models.dtos.requests.UserDtoAdd;
import edu.utn.utnPhones.models.enums.BillStatus;
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

        return UserDtoAdd
                .builder()
                .userType("client")
                .city("Mar del Plata")
                .areaCode("223")
                .province("Buenos Aires")
                .dni("42454677")
                .build();
    }
}

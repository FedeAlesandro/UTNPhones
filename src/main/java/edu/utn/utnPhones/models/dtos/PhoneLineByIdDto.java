package edu.utn.utnPhones.models.dtos;

import edu.utn.utnPhones.models.LineType;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.PhoneLineStatus;
import lombok.Data;

import java.util.List;

@Data
public class PhoneLineByIdDto {

    private Integer id;

    private String userName;

    private String phoneNumber;

    private LineType lineType;

    private PhoneLineStatus state;

    private List<BillsPhoneLineDto> bills;

    private List<PhoneCallPhoneLineDto> originPhoneCalls;

    private List<PhoneCallPhoneLineDto> destinationPhoneCalls;

    public PhoneLineByIdDto(PhoneLine phoneLine, List<BillsPhoneLineDto> bills, List<PhoneCallPhoneLineDto> originPhoneCalls, List<PhoneCallPhoneLineDto> destinationPhoneCalls) {
        id = phoneLine.getId();
        userName = phoneLine.getUser().getUserName();
        phoneNumber = phoneLine.getPhoneNumber();
        lineType = phoneLine.getLineType();
        state = phoneLine.getState();
        this.bills = bills;
        this.originPhoneCalls = originPhoneCalls;
        this.destinationPhoneCalls = destinationPhoneCalls;
    }
}

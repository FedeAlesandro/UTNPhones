package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.enums.LineType;
import edu.utn.utnPhones.models.enums.PhoneLineStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneLineDtoResponse {

    private Integer id;

    private String userName;

    private String phoneNumber;

    private LineType lineType;

    private PhoneLineStatus state;

    public static PhoneLineDtoResponse fromPhoneLine(PhoneLine phoneLine){

        return PhoneLineDtoResponse.builder()
                .id(phoneLine.getId())
                .userName(phoneLine.getUser().getUserName())
                .phoneNumber(phoneLine.getPhoneNumber())
                .lineType(phoneLine.getLineType())
                .state(phoneLine.getState())
                .build();
    }
}

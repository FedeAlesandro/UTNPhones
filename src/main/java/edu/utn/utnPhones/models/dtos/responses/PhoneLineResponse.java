package edu.utn.utnPhones.models.dtos.responses;

import edu.utn.utnPhones.models.LineType;
import edu.utn.utnPhones.models.PhoneLine;
import edu.utn.utnPhones.models.PhoneLineStatus;
import lombok.Data;

@Data
public class PhoneLineResponse {

    private Integer id;

    private String userName;

    private String phoneNumber;

    private LineType lineType;

    private PhoneLineStatus state;

    public PhoneLineResponse(PhoneLine phoneLine){
        this.id = phoneLine.getId();
        this.userName = phoneLine.getUser().getUserName();
        this.phoneNumber = phoneLine.getPhoneNumber();
        this.lineType = phoneLine.getLineType();
        this.state = phoneLine.getState();
    }
}

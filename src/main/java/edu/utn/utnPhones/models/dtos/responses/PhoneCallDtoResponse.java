package edu.utn.utnPhones.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import edu.utn.utnPhones.models.PhoneCall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCallDtoResponse {

    private Integer id;

    private Integer duration;

    private BigDecimal totalPrice;

    private BigDecimal totalCost;

    private String originPhoneNumber;

    private String destinationPhoneNumber;

    @JsonFormat(timezone = "GMT-03:00", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date date;

    public static PhoneCallDtoResponse fromPhoneCall(PhoneCall phoneCall){

        return PhoneCallDtoResponse.builder()
                .id(phoneCall.getId())
                .duration(phoneCall.getDuration())
                .totalPrice(phoneCall.getTotalPrice())
                .totalCost(phoneCall.getTotalCost())
                .originPhoneNumber(phoneCall.getOriginPhoneNumber())
                .destinationPhoneNumber(phoneCall.getDestinationPhoneNumber())
                .date(phoneCall.getDate())
                .build();
    }
}

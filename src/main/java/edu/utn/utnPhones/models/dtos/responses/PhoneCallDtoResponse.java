package edu.utn.utnPhones.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
}

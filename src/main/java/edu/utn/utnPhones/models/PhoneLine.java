package edu.utn.utnPhones.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.utn.utnPhones.models.dtos.PhoneLineAdd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "phone_lines")
public class PhoneLine {

    @Id
    @Column(name = "id_phone_line")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @JsonBackReference(value = "phoneLineUser")
    private User user;

    @NotBlank
    @Size(min = 9, max = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LineType lineType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PhoneLineStatus state;

    @OneToMany(mappedBy = "phoneLine")
    private List<Bill> bills;

    @OneToMany(mappedBy = "originPhoneLine")
    private List<PhoneCall> originPhoneCalls;

    @OneToMany(mappedBy = "destinationPhoneLine")
    private List<PhoneCall> destinationPhoneCalls;

    public static PhoneLine fromPhoneLineAdd(PhoneLineAdd phoneLine){
        return PhoneLine.builder()
                .user(phoneLine.getUser())
                .phoneNumber(phoneLine.getPhoneNumber())
                .lineType(phoneLine.getLineType())
                .state(PhoneLineStatus.register)
                .build();
    }

}

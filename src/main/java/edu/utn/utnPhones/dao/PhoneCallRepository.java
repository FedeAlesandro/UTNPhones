package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.CallsByDateRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall,Integer> {

    @Query(value = "u.user_name userName, pc.id_phone_call id, pc.total_cost totalCost, pc.total_price totalPrice, pc.duration duration, pc.date_call date\n" +
            "from phone_calls as pc\n" +
            "join phone_lines as pl\n" +
            "on\tpc.id_origin_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 AND pc.date_call BETWEEN ?2 AND ?3;\n" +
            "group by (u.user_name);", nativeQuery = true)
    List<CallsByDateRange> getCallsByDateRange(Integer idUser, LocalDate date1, LocalDate date2);

}

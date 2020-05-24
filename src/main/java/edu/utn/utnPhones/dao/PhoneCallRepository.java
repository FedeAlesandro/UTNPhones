package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.CallsByDateRange;
import edu.utn.utnPhones.projections.CallsByUser;
import edu.utn.utnPhones.projections.MostCalledDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall,Integer> {

    @Query(value = "select u.user_name userName, pc.total_price totalPrice, pc.duration duration, pc.date_call date\n" +
            "from phone_calls as pc\n" +
            "join phone_lines as pl\n" +
            "on\tpc.id_origin_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 AND pc.date_call BETWEEN ?2 AND ?3 ;"
            , nativeQuery = true)
    List<CallsByDateRange> getCallsByDateRange(Integer idUser, LocalDate date1, LocalDate date2);

    @Query(value = "select pld.phone_number phoneNumber, count(*) callsCount from phone_calls pc join phone_lines pld" +
            " on pc.id_destination_phone_line = pld.id_phone_line join phone_lines plo" +
            " on pc.id_origin_phone_line = plo.id_phone_line join users u on u.id_user = plo.id_user" +
            " where u.id_user = ?1 group by pld.phone_number order by count(*) desc limit 10", nativeQuery = true)
    List<MostCalledDestination> getMostCalledDestinations(Integer idUser);

    @Query(value = "select u.user_name userName, pc.total_cost totalCost, pc.total_price totalPrice, pc.duration duration, pc.date_call date\n" +
            "from phone_calls as pc\n" +
            "join phone_lines as pl\n" +
            "on pc.id_origin_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1;"
            , nativeQuery = true)
    List<CallsByUser> getByUser(Integer idUser);
}

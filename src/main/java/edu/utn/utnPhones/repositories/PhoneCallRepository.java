package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.PhoneCall;
import edu.utn.utnPhones.models.projections.CallsByDateRange;
import edu.utn.utnPhones.models.projections.CallsByUser;
import edu.utn.utnPhones.models.projections.MostCalledDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall,Integer> {

    @Query(value = "select u.user_name userName, pc.total_price totalPrice, pc.duration duration, pc.date_call date, " +
            "pc.origin_phone_number originNumber, pc.destination_phone_number destinationNumber\n" +
            "from phone_calls as pc\n" +
            "join phone_lines as pl\n" +
            "on pc.id_origin_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 AND pc.date_call BETWEEN ?2 AND ?3 ;"
            , nativeQuery = true)
    List<CallsByDateRange> getCallsByDateRange(Integer idUser, Date date1, Date date2);

    @Query(value = "select pld.phone_number phoneNumber, ud.name, count(*) callsCount " +
            "from phone_calls pc " +
            "join phone_lines pld " +
            "on pc.id_destination_phone_line = pld.id_phone_line " +
            "join users ud " +
            "on pld.id_user = ud.id_user " +
            "join phone_lines plo " +
            "on pc.id_origin_phone_line = plo.id_phone_line " +
            "join users uo " +
            "on uo.id_user = plo.id_user " +
            "where uo.id_user = ?1 " +
            "group by pld.phone_number " +
            "order by count(*) desc " +
            "limit 10 ;", nativeQuery = true)
    List<MostCalledDestination> getMostCalledDestinations(Integer idUser);

    @Query(value = "select u.user_name userName, pc.total_cost totalCost, pc.total_price totalPrice, pc.duration duration, pc.date_call date, " +
            "pc.origin_phone_number originNumber, pc.destination_phone_number destinationNumber\n" +
            "from phone_calls as pc\n" +
            "join phone_lines as pl\n" +
            "on pc.id_origin_phone_line = pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 ;"
            , nativeQuery = true)
    List<CallsByUser> getByUser(Integer idUser);

    @Query(value = "select pc.date_call dateCall from phone_calls pc where pc.id_phone_call = ?1 ;", nativeQuery = true)
    Date getDateById(Integer idPhoneCall);
}

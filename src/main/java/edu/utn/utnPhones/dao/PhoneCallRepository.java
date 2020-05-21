package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.PhoneCall;
import edu.utn.utnPhones.projections.MostCalledDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall,Integer> {

    @Query(value = "select pld.phone_number phoneNumber, count(*) callsCount from phone_calls pc join phone_lines pld" +
            " on pc.id_destination_phone_line = pld.id_phone_line join phone_lines plo" +
            " on pc.id_origin_phone_line = plo.id_phone_line join users u on u.id_user = plo.id_user" +
            " where u.id_user = ?1 group by pld.phone_number order by count(*) desc limit 10", nativeQuery = true)
    List<MostCalledDestination> getMostCalledDestinations(Integer id);
}

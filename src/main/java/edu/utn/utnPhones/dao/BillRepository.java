package edu.utn.utnPhones.dao;

import edu.utn.utnPhones.model.Bill;
import edu.utn.utnPhones.projections.BillsByDateRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    @Query(value = "u.user_name userName, b.id_bill id, b.calls_amount callsAmount, b.total_cost totalCost, b.total_price totalPrice, b.bill_date date, b.bill_expiration dateExpiration, b.state state\n" +
            "from bills as b\n" +
            "join phone_lines as pl\n" +
            "on b.id_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 AND pc.date_call BETWEEN ?2 AND ?3\n" +
            "group by (u.user_name);", nativeQuery = true)
    List<BillsByDateRange> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2);
}

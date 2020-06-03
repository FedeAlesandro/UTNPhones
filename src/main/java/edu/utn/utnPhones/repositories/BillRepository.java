package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.Bill;
import edu.utn.utnPhones.models.projections.BillsForUsers;
import edu.utn.utnPhones.models.projections.BillsWithoutPhoneCalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    @Query(value = "select b.calls_amount callsAmount, b.total_cost totalCost, b.total_price totalPrice" +
            ", b.bill_date billDate, b.bill_expiration billExpiration, b.state state" +
            " from bills b ;", nativeQuery = true)
    List<BillsWithoutPhoneCalls> getBills();

    @Query(value = "select u.user_name userName, b.calls_amount callsAmount, b.total_price total_price, b.bill_date date, b.bill_expiration dateExpiration\n" +
            "from bills as b\n" +
            "join phone_lines as pl\n" +
            "on b.id_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 AND b.bill_date  BETWEEN ?2 AND ?3 ;"
            , nativeQuery = true)
    List<BillsForUsers> getBillsByDateRange(Integer idUser, LocalDate date1, LocalDate date2);

    @Query(value = "select * " +
                   "from bills b " +
                   "where b.id_phone_line = ?1 ;"
                   , nativeQuery = true)
    Optional<List<Bill>> findByIdPhoneLine(Integer idPhoneLine);

    @Query(value = "select u.user_name userName, b.calls_amount callsAmount, b.total_price totalPrice, b.bill_date date, b.bill_expiration dateExpiration\n" +
            "from bills as b\n" +
            "join phone_lines as pl\n" +
            "on b.id_phone_line=pl.id_phone_line\n" +
            "join users as u\n" +
            "on pl.id_user = u.id_user\n" +
            "where u.id_user = ?1 ;"
            , nativeQuery = true)
    List<BillsForUsers> getBillsByUser(Integer idUser);
}

package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.UserType;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.models.projections.UsersWithoutPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.id_user id , u.name name, u.last_name lastName, u.dni dni, u.user_name userName, u.user_type userType" +
                   ", u.removed_user removed, c.city_name cityName from users u join cities c on u.id_city = c.id_city ;", nativeQuery = true)
    List<UsersWithoutPassword> getAll();

    List<ClientsWithoutPassword> findByUserTypeAndRemoved(UserType client, Boolean removed);
}

package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.UserType;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.models.projections.UsersWithoutPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.id_user id , u.name name, u.last_name lastName, u.dni dni, u.user_name userName, u.user_type userType" +
                   ", u.removed_user removed, c.city_name cityName from users u join cities c on u.id_city = c.id_city ;", nativeQuery = true)
    List<UsersWithoutPassword> getAll();

    List<ClientsWithoutPassword> findByUserTypeAndRemoved(UserType client, Boolean removed);

    User findByDniAndUserType(String dni, UserType userType);

    Optional<User> findByIdAndRemoved(Integer idUser, boolean removed);

    @Query(value = "select count(*) > 0 from users u where u.id_user <> ?1 and u.dni = ?2 and u.user_type = ?3 and u.removed_user = ?4 ;", nativeQuery = true)
    Integer existsByIdAndDniAndUserTypeAndRemoved(Integer id, String dni, UserType userType, boolean removed);

    @Query(value = "select * from users u where u.user_name = ?1 and u.removed_user = ?2 ;", nativeQuery = true)
    User findByUserNameAndRemoved(String userName, boolean removed);

    @Query(value = "select * from users u where u.user_name = ?1 and u.removed_user = ?2 and u.id_user <> ?3 ;", nativeQuery = true)
    User findByIdAndUserNameAndRemoved(String userName, boolean removed, Integer idUser);

    @Query(value = "select u.id_user idUser from users u where u.user_name = ?1 and u.removed_user = ?2 ;", nativeQuery = true)
    Integer getIdByUserName(String userName, boolean removed);

    Optional<User> findByUserNameAndRemovedAndUserType(String userName, boolean removed, UserType userType);
}

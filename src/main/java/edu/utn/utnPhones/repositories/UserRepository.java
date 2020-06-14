package edu.utn.utnPhones.repositories;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.enums.UserType;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<ClientsWithoutPassword> findByUserTypeAndRemoved(UserType client, Boolean removed);

    Optional<User> findByIdAndRemoved(Integer idUser, boolean removed);

    @Query(value = "select * from users u where u.user_name = ?1 and u.removed_user = ?2 ;", nativeQuery = true)
    User findByUserNameAndRemoved(String userName, boolean removed);

    @Query(value = "select * from users u where u.user_name = ?1 and u.removed_user = ?2 and u.id_user <> ?3 ;", nativeQuery = true)
    User findByIdAndUserNameAndRemoved(String userName, boolean removed, Integer idUser);

    @Query(value = "select u.id_user idUser from users u where u.user_name = ?1 and u.removed_user = ?2 ;", nativeQuery = true)
    Integer getIdByUserName(String userName, boolean removed);

    Optional<User> findByUserNameAndRemovedAndUserType(String userName, boolean removed, UserType userType);

    User findByDni(String dni);

    @Query(value = "select * from users u where u.dni = ?1 and u.id_user <> ?2 ;", nativeQuery = true)
    User findByDniAndId(String dni, Integer idUser);

    Optional<User> findByUserNameAndPwd(String userName, String pwd);
}

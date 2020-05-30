package edu.utn.utnPhones.services;

import edu.utn.utnPhones.repositories.CityRepository;
import edu.utn.utnPhones.repositories.UserRepository;
import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.UserType;
import edu.utn.utnPhones.models.dtos.UserUpdateDto;
import edu.utn.utnPhones.models.projections.ClientsWithoutPassword;
import edu.utn.utnPhones.models.projections.UsersWithoutPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CityRepository cityRepository;

    @Autowired
    public UserService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public List<UsersWithoutPassword> getAll(){
        return userRepository.getAll();
    }

    public void add(User user){
        userRepository.save(user);
    }

    public List<ClientsWithoutPassword> getClients() {
        return userRepository.findByUserTypeAndRemoved(UserType.client, false);
    }

    public void remove(Integer idUser) {

        User deletedUser = Optional.ofNullable(userRepository.findById(idUser))
                .get()
                .orElseThrow(RuntimeException::new); //Aca va una excepcion creada por nosotros

        deletedUser.setRemoved(true);
        userRepository.save(deletedUser);
    }

    public void update(Integer idUser, UserUpdateDto updatedUser) {

        User oldUser = Optional.ofNullable(userRepository.findById(idUser))
                .get()
                .orElseThrow(RuntimeException::new); //Aca va una excepcion creada por nosotros

        if (updatedUser.getName() != null){
            oldUser.setName(updatedUser.getName());
        }
        if (updatedUser.getLastName() != null){
            oldUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDni() != null){
            oldUser.setDni(updatedUser.getDni());
        }
        if (updatedUser.getUserName() != null){
            oldUser.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getPwd() != null){
            oldUser.setPwd(updatedUser.getPwd());
        }
        if (updatedUser.getCity() != null){
            if (cityRepository.existsById(updatedUser.getCity().getId())){
                oldUser.setCity(updatedUser.getCity());
            } else {
                throw new RuntimeException(); //Aca va una excepcion creada por nosotros
            }
        }

        userRepository.save(oldUser);
    }
}

package edu.utn.utnPhones.service;

import edu.utn.utnPhones.dao.UserRepository;
import edu.utn.utnPhones.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void add(User user){
        userRepository.save(user);
    }
}

package com.example.chat_26_5.service;

import com.example.chat_26_5.model.UserModel;
import com.example.chat_26_5.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel registerUser(String name, String email, String password){
        if(email==null || password==null){
            return null;
        }else{
            if(userRepository.findFirstByEmail(email).isPresent()){
                System.out.println("email already exists");
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setEmail(email);
            userModel.setPassword(password);
            return userRepository.save(userModel);
        }
    }

    public UserModel authenticate(String email, String password){
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }
}

package com.example.chat_26_5.repository;

import com.example.chat_26_5.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmailAndPassword(String email, String password);

    Optional<UserModel> findFirstByEmail(String email);
}

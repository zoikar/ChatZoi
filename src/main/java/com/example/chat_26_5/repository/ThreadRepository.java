package com.example.chat_26_5.repository;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ThreadModel, Integer> {
    Optional<ThreadModel> findByIdAndUser_Id(Integer th_id, Integer userId);

    List<ThreadModel> findAllByUser_Id(Integer userId);

    List<ThreadModel> findByUserId(Integer userId);

    Optional<ThreadModel> findByIdAndUserId(Integer id, Integer userId);

}

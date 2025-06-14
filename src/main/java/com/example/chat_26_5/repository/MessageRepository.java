package com.example.chat_26_5.repository;

import com.example.chat_26_5.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {
    List<MessageModel> findAllByThread_IdAndUser_Id(Integer threadId, Integer userId);
}

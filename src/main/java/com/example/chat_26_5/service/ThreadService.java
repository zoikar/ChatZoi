package com.example.chat_26_5.service;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    public ThreadModel getThreadForUser(Integer threadId, Integer userId) {
        threadRepository.findByIdAndUser_Id(threadId,userId)
                .orElseThrow(() -> new RuntimeException("Thread not found for this user"));
        System.out.println("Thread found for this user " + threadId + " " + userId);
        return null;
    }

    public List<ThreadModel> getThreadsByUserId(Integer userId) {
        return threadRepository.findAllByUser_Id(userId);
    }

}

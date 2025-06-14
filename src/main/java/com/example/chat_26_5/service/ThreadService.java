package com.example.chat_26_5.service;

import com.example.chat_26_5.model.MessageModel;
import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.model.UserModel;
import com.example.chat_26_5.repository.MessageRepository;
import com.example.chat_26_5.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private MessageRepository messageRepository;

    public ThreadModel getThreadForUser(Integer threadId, Integer userId) {
        threadRepository.findByIdAndUser_Id(threadId,userId)
                .orElseThrow(() -> new RuntimeException("Thread not found for this user"));
        System.out.println("Thread found for this user " + threadId + " " + userId);
        return null;
    }

    public List<MessageModel> getMessagesForThreadAndUser(Integer threadId, Integer userId) {
        return messageRepository.findAllByThread_IdAndUser_Id(threadId, userId);
    }

    public void deleteThreadById(Integer id) {
        threadRepository.deleteById(id);
    }

    public List<ThreadModel> getAllThreads() {
        return threadRepository.findAll(); // Παίρνει όλα τα threads από τη βάση
    }

    public List<ThreadModel> getThreadsByUserId(Integer userId) {
        return threadRepository.findByUserId(userId);
    }

    public void deleteThread(Integer id) {
        threadRepository.deleteById(id);
    }
    public boolean deleteThreadByIdForUser(Integer threadId, Integer userId) {
        Optional<ThreadModel> threadOpt = threadRepository.findByIdAndUserId(threadId, userId);

        if (threadOpt.isPresent()) {
            threadRepository.delete(threadOpt.get());
            return true;
        }
        return false; // Δεν έγινε διαγραφή (είτε δεν υπάρχει, είτε δεν ανήκει στον χρήστη)
    }
    public ThreadModel saveThread(ThreadModel thread) {
        return threadRepository.save(thread);
    }

    public ThreadModel createNewThreadForUser(Integer userId) {
        Integer maxThId = threadRepository.findMaxThIdByUserId(userId);
        int nextThId = (maxThId != null ? maxThId : 0) + 1;

        ThreadModel newThread = new ThreadModel();
        newThread.setTh_name("New Chat " + nextThId);

        // Ορισμός ημερομηνίας με μορφοποίηση d/M/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        newThread.setTh_created(LocalDate.now().format(formatter));

        newThread.setThread_id(nextThId);

        UserModel user = new UserModel();
        user.setId(userId);
        newThread.setUser(user);

        return threadRepository.save(newThread);
    }



}

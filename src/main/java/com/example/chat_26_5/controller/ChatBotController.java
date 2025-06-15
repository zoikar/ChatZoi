package com.example.chat_26_5.controller;

import com.example.chat_26_5.model.MessageModel;
import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.repository.MessageRepository;
import com.example.chat_26_5.repository.ThreadRepository;
import com.example.chat_26_5.service.AiChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chatbot")
public class ChatBotController {

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestParam Integer threadId,
            @RequestParam Integer userId,
            @RequestParam String message,
            HttpSession session) {

        ThreadModel thread = threadRepository.findById(threadId).orElse(null);
        if (thread == null || !thread.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Invalid thread or user");
        }

        MessageModel userMessage = new MessageModel();
        userMessage.setContent(message);
        userMessage.setSender("user");
        userMessage.setDate(LocalDateTime.now());
        userMessage.setThread(thread);
        userMessage.setUser(thread.getUser());

        messageRepository.save(userMessage);

        String aiResponseText = aiChatService.getAiResponse(message);

        MessageModel aiMessage = new MessageModel();
        aiMessage.setContent(aiResponseText);
        aiMessage.setSender("ai");
        aiMessage.setDate(LocalDateTime.now());
        aiMessage.setThread(thread);
        aiMessage.setUser(thread.getUser());

        messageRepository.save(aiMessage);

        return ResponseEntity.ok(aiMessage);
    }
}

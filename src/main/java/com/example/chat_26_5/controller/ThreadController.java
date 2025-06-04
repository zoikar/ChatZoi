package com.example.chat_26_5.controller;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.repository.ThreadRepository;
import com.example.chat_26_5.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @GetMapping("/{threadId}/user/{userId}")
    public ResponseEntity<ThreadModel> getThread(
            @PathVariable Integer threadId,
            @PathVariable Integer userId) {

        ThreadModel thread = threadService.getThreadForUser(threadId, userId);
        return ResponseEntity.ok(thread);
    }
}

package com.example.chat_26_5.controller;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.service.ThreadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@Controller
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    // Προβολή συγκεκριμένου thread (π.χ. μέσω API)
    @GetMapping("/{threadId}/user/{userId}")
    public ResponseEntity<ThreadModel> getThread(
            @PathVariable Integer threadId,
            @PathVariable Integer userId) {

        ThreadModel thread = threadService.getThreadForUser(threadId, userId);
        return ResponseEntity.ok(thread);
    }

    @PostMapping("/{id}/delete")
    public String deleteThread(@PathVariable Integer id, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        boolean deleted = threadService.deleteThreadByIdForUser(id, userId);
        if (!deleted) {
            model.addAttribute("error", "Δεν επιτρέπεται η διαγραφή αυτού του thread.");
        }

        List<ThreadModel> threads = threadService.getThreadsByUserId(userId);
        model.addAttribute("threads", threads);
        model.addAttribute("activeThreadId", null);
        return "chat_page";
    }


    // Εμφάνιση thread ως ενεργό
    @GetMapping("/{id}/select")
    public String selectThread(@PathVariable Integer id, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<ThreadModel> threads = threadService.getThreadsByUserId(userId);
        model.addAttribute("threads", threads);
        model.addAttribute("activeThreadId", id);
        return "chat_page";
    }
}

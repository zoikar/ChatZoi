package com.example.chat_26_5.controller;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.model.UserModel;
import com.example.chat_26_5.service.ThreadService;
import com.example.chat_26_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ThreadService threadService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel) {
        System.out.println("register request received: " + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getName(), userModel.getEmail(), userModel.getPassword());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model) {
        System.out.println("Login request received: " + userModel);
        UserModel authenticatedUser = userService.authenticate(userModel.getEmail(), userModel.getPassword());

        if (authenticatedUser != null) {
            model.addAttribute("userLogin", authenticatedUser.getName());

            // Προσθήκη: Φέρε όλα τα threads του χρήστη

            List<ThreadModel> userThreads = threadService.getThreadsByUserId(authenticatedUser.getId());
            model.addAttribute("threads", userThreads);

            return "chat_page";
        } else {
            return "error_page";
        }
    }

}

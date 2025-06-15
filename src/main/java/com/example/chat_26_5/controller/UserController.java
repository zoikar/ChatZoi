package com.example.chat_26_5.controller;

import com.example.chat_26_5.model.ThreadModel;
import com.example.chat_26_5.model.UserModel;
import com.example.chat_26_5.service.ThreadService;
import com.example.chat_26_5.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String login(@ModelAttribute UserModel userModel, Model model, HttpSession session) {
        UserModel authenticatedUser = userService.authenticate(userModel.getEmail(), userModel.getPassword());

        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("userId", authenticatedUser.getId());
            model.addAttribute("userLogin", authenticatedUser.getName());

            List<ThreadModel> userThreads = threadService.getThreadsByUserId(authenticatedUser.getId());
            model.addAttribute("threads", userThreads);

            return "chat_page";
        } else {
            return "error_page";
        }
    }

    @GetMapping("/profile_page")
    public String showProfile(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile_page";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/settings")
    public String getSettingsPage(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserModel formUser, HttpSession session, Model model) {
        UserModel loggedUser = (UserModel) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        UserModel userFromDb = userService.findById(loggedUser.getId());
        if (userFromDb == null) {
            return "error_page";
        }

        userFromDb.setName(formUser.getName());
        userFromDb.setEmail(formUser.getEmail());

        if (formUser.getPassword() != null && !formUser.getPassword().isEmpty()) {
            userFromDb.setPassword(formUser.getPassword());
        }

        userService.save(userFromDb);

        session.setAttribute("user", userFromDb);

        model.addAttribute("user", userFromDb);
        model.addAttribute("successMessage", "Profile updated successfully!");

        return "profile_page";
    }

    @GetMapping("/chat_page")
    public String showChatPage(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<ThreadModel> userThreads = threadService.getThreadsByUserId(user.getId());
        model.addAttribute("threads", userThreads);
        model.addAttribute("user", user);

        return "chat_page";
    }



}

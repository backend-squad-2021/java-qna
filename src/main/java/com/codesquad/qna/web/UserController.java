package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.codesquad.qna.web.HttpSessionUtils.USER_SESSION_KEY;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;
import static com.codesquad.qna.web.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/users")
public class UserController {
    public static final String REDIRECT_LOGIN_FORM = "redirect:/users/loginForm";
    private static final String REDIRECT_USERS_DATA = "redirect:/users/list";
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            return REDIRECT_LOGIN_FORM;
        }

        User user = optionalUser.get();
        if (!user.isPasswordEquals(password)) {
            return REDIRECT_LOGIN_FORM;
        }
        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(User user, Model model) {
        User createdUser = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
        userRepository.save(createdUser);
        return REDIRECT_USERS_DATA;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/checkForm")
    public String checkPasswordForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);

        model.addAttribute("user", sessionedUser);
        return "/user/checkForm";
    }

    @PostMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, String password, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);

        if (!sessionedUser.isPasswordEquals(password)) {
            return "redirect:/users/{id}/checkForm";
        }

        model.addAttribute("user", sessionedUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);
        sessionedUser.update(updateUser);

        userRepository.save(sessionedUser);
        return REDIRECT_USERS_DATA;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "/user/profile";
    }
}

package ru.komogorova.birthday_manager.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.komogorova.birthday_manager.dto.UserDto;
import ru.komogorova.birthday_manager.entity.User;
import ru.komogorova.birthday_manager.service.UserService;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result, Model model) {

        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            result.rejectValue("username", null,
                    "Пользователь с таким логином уже зарегистрирован");
        }

        User existingEmail = userService.findByEmail(userDto.getEmail());
        if (existingEmail != null) {
            result.rejectValue("email", null,
                    "Пользователь с таким email уже зарегистрирован");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null,
                    "Пароли не совпадают");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/login?registered";
    }
}
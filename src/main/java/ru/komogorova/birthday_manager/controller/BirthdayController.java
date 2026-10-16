package ru.komogorova.birthday_manager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komogorova.birthday_manager.entity.Birthday;
import ru.komogorova.birthday_manager.entity.User;
import ru.komogorova.birthday_manager.repository.BirthdayRepository;
import ru.komogorova.birthday_manager.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/birthdays")
public class BirthdayController {

    private final BirthdayRepository birthdayRepository;
    private final UserRepository userRepository;

    private static final List<Map<String, Object>> RELATIONSHIPS = List.of(
            Map.of("code", 0, "name", "Приятель"),
            Map.of("code", 1, "name", "Друг"),
            Map.of("code", 2, "name", "Коллега"),
            Map.of("code", 3, "name", "Родственник"),
            Map.of("code", 4, "name", "Другое")
    );

    public BirthdayController(BirthdayRepository birthdayRepository, UserRepository userRepository) {
        this.birthdayRepository = birthdayRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String list(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("birthdays", birthdayRepository.findByUserId(user.getId()));
        return "birthdays/birthday_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("birthday", new Birthday());
        model.addAttribute("relationships", RELATIONSHIPS);
        return "birthdays/form_birthday";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Birthday birthday, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());

        if (birthday.getId() != null) {
            Birthday existing = birthdayRepository.findById(birthday.getId()).orElseThrow();

            if (!existing.getUser().getId().equals(user.getId())) {
                return "redirect:/birthdays";
            }

            existing.setName(birthday.getName());
            existing.setSurname(birthday.getSurname());
            existing.setBirthDate(birthday.getBirthDate());
            existing.setRelationship(birthday.getRelationship());

            birthdayRepository.save(existing);
        } else {
            birthday.setUser(user);
            birthdayRepository.save(birthday);
        }

        return "redirect:/birthdays";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, Authentication auth) {
        Birthday birthday = birthdayRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (!birthday.getUser().getId().equals(user.getId())) {
            return "redirect:/birthdays";
        }
        model.addAttribute("birthday", birthday);
        model.addAttribute("relationships", RELATIONSHIPS);
        return "birthdays/form_birthday";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Authentication auth) {
        Birthday birthday = birthdayRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (birthday.getUser().getId().equals(user.getId())) {
            birthdayRepository.delete(birthday);
        }
        return "redirect:/birthdays";
    }
}
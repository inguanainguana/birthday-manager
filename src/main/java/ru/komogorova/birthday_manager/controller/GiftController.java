package ru.komogorova.birthday_manager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komogorova.birthday_manager.entity.Birthday;
import ru.komogorova.birthday_manager.entity.Gift;
import ru.komogorova.birthday_manager.entity.User;
import ru.komogorova.birthday_manager.repository.BirthdayRepository;
import ru.komogorova.birthday_manager.repository.GiftRepository;
import ru.komogorova.birthday_manager.repository.UserRepository;

@Controller
@RequestMapping("/gifts")
public class GiftController {

    private final GiftRepository giftRepository;
    private final BirthdayRepository birthdayRepository;
    private final UserRepository userRepository;

    public GiftController(GiftRepository giftRepository,
                          BirthdayRepository birthdayRepository,
                          UserRepository userRepository) {
        this.giftRepository = giftRepository;
        this.birthdayRepository = birthdayRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String list(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("gifts", giftRepository.findByUserId(user.getId()));
        return "gifts/list_gifts";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("gift", new Gift());
        model.addAttribute("birthdays", birthdayRepository.findByUserId(user.getId()));
        return "gifts/form_gifts";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Gift gift,
                       @RequestParam("birthdayId") Integer birthdayId,
                       Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        Birthday birthday = birthdayRepository.findById(birthdayId).orElseThrow();

        if (gift.getId() != null) {
            Gift existing = giftRepository.findById(gift.getId()).orElseThrow();
            if (!existing.getUser().getId().equals(user.getId())) {
                return "redirect:/gifts";
            }
            gift.setUser(existing.getUser());
        } else {
            gift.setUser(user);
        }
        gift.setBirthday(birthday);
        giftRepository.save(gift);
        return "redirect:/gifts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, Authentication auth) {
        Gift gift = giftRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (!gift.getUser().getId().equals(user.getId())) {
            return "redirect:/gifts";
        }
        model.addAttribute("gift", gift);
        model.addAttribute("birthdays", birthdayRepository.findByUserId(user.getId()));
        return "gifts/form_gifts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Authentication auth) {
        Gift gift = giftRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (gift.getUser().getId().equals(user.getId())) {
            giftRepository.delete(gift);
        }
        return "redirect:/gifts";
    }
}
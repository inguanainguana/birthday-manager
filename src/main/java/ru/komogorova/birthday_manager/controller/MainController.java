package ru.komogorova.birthday_manager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.komogorova.birthday_manager.entity.Birthday;
import ru.komogorova.birthday_manager.entity.User;
import ru.komogorova.birthday_manager.repository.BirthdayRepository;
import ru.komogorova.birthday_manager.repository.GiftRepository;
import ru.komogorova.birthday_manager.repository.UserRepository;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {

    private final UserRepository userRepository;
    private final BirthdayRepository birthdayRepository;
    private final GiftRepository giftRepository;

    public MainController(UserRepository userRepository,
                          BirthdayRepository birthdayRepository,
                          GiftRepository giftRepository) {
        this.userRepository = userRepository;
        this.birthdayRepository = birthdayRepository;
        this.giftRepository = giftRepository;
    }


    @GetMapping("/cabinet")
    public String dashboard(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        List<Birthday> birthdays = birthdayRepository.findByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("birthdayCount", birthdays.size());
        model.addAttribute("giftCount", giftRepository.findByUserId(user.getId()).size());

        Birthday nearest = findNearestBirthday(birthdays);
        model.addAttribute("nearest", nearest);

        return "cabinet";
    }

    private Birthday findNearestBirthday(List<Birthday> birthdays) {
        LocalDate today = LocalDate.now();
        return birthdays.stream()
                .min(Comparator.comparing(b -> daysUntil(b.getBirthDate(), today)))
                .orElse(null);
    }

    private long daysUntil(LocalDate birthDate, LocalDate today) {
        MonthDay birthDay = MonthDay.of(birthDate.getMonth(), birthDate.getDayOfMonth());

        LocalDate nextBirthday = birthDay.atYear(today.getYear());
        if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }
        return ChronoUnit.DAYS.between(today, nextBirthday);
    }
}
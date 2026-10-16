package ru.komogorova.birthday_manager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.komogorova.birthday_manager.entity.Birthday;
import ru.komogorova.birthday_manager.entity.User;
import ru.komogorova.birthday_manager.repository.BirthdayRepository;
import ru.komogorova.birthday_manager.repository.UserRepository;
import ru.komogorova.birthday_manager.service.BudgetService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;
    private final BirthdayRepository birthdayRepository;

    public BudgetController(BudgetService budgetService,
                            UserRepository userRepository,
                            BirthdayRepository birthdayRepository) {
        this.budgetService = budgetService;
        this.userRepository = userRepository;
        this.birthdayRepository = birthdayRepository;
    }

    @GetMapping
    public String showBudget(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        BigDecimal total = budgetService.calculateTotalForUser(user.getId());
        List<Object[]> breakdown = budgetService.getBudgetByBirthday(user.getId());

        model.addAttribute("total", total);
        model.addAttribute("breakdown", breakdown);
        return "budget";
    }
}
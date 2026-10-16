package ru.komogorova.birthday_manager.service;

import org.springframework.stereotype.Service;
import ru.komogorova.birthday_manager.repository.GiftRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetService {

    private final GiftRepository giftRepository;

    public BudgetService(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public BigDecimal calculateTotalForUser(Integer userId) {
        BigDecimal total = giftRepository.sumPriceByUserId(userId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public List<Object[]> getBudgetByBirthday(Integer userId) {
        return giftRepository.findBudgetByBirthday(userId);
    }
}
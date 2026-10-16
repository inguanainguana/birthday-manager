package ru.komogorova.birthday_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.komogorova.birthday_manager.entity.Gift;

import java.math.BigDecimal;
import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Integer> {
    List<Gift> findByUserId(Integer userId);
    List<Gift> findByBirthdayId(Integer birthdayId);
    @Query("SELECT SUM(g.price) FROM Gift g WHERE g.user.id = :userId")
    BigDecimal sumPriceByUserId(@Param("userId") Integer userId);

    @Query("SELECT SUM(g.price) FROM Gift g WHERE g.birthday.id = :birthdayId")
    BigDecimal sumPriceByBirthdayId(@Param("birthdayId") Integer birthdayId);
    @Query("SELECT g.birthday.name, g.birthday.surname, COUNT(g), SUM(g.price) " +
            "FROM Gift g " +
            "WHERE g.user.id = :userId " +
            "GROUP BY g.birthday.id, g.birthday.name, g.birthday.surname " +
            "ORDER BY SUM(g.price) DESC")
    List<Object[]> findBudgetByBirthday(@Param("userId") Integer userId);
}

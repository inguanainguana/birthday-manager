package ru.komogorova.birthday_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komogorova.birthday_manager.entity.Birthday;

import java.util.List;

public interface BirthdayRepository extends JpaRepository<Birthday, Integer> {
    List<Birthday> findByUserId(Integer userId);
}
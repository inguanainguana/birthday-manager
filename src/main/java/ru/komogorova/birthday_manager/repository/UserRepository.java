package ru.komogorova.birthday_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.komogorova.birthday_manager.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail (String email);
}

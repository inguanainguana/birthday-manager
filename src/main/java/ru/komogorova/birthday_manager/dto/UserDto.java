package ru.komogorova.birthday_manager.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 3, max = 50, message = "Логин должен содержать от 3 до 50 символов")
    private String username;

    @NotEmpty(message = "Почта не должна быть пустой")
    @Email(message = "Неправильно введенная почта")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotEmpty(message = "Повторите пароль")
    private String confirmPassword;
}

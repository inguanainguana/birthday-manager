# Курсовой проект по дисциплине «Программирование».

Веб-приложение для учёта дней рождения и планирования бюджета подарков.

## Требования

- JDK 17 или выше
- Maven 3.8+
- MySQL 8.0+

## Запуск

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/ваш-логин/birthday-manager.git
   cd birthday-manager
   ```

2. Создайте базу данных в MySQL. Выполните скрипт из файла `schema.sql`:

   ```bash
   mysql -u root -p < schema.sql
   ```

3. Откройте файл `src/main/resources/application.properties` и укажите логин и пароль от вашей MySQL:

   ```properties
   spring.datasource.username=root
   spring.datasource.password=ваш_пароль
   ```

4. Соберите и запустите приложение:

   ```bash
   mvn spring-boot:run
   ```

   Или запустите класс `BirthdayManagerApplication` в IDE.

5. Откройте в браузере:

   ```
   http://localhost:8080
   ```

## Использование

1. Зарегистрируйтесь — новому пользователю автоматически присваивается роль READ_ONLY.
2. Для получения прав на создание и редактирование записей обратитесь к администратору.
3. Администратор может изменить роль через раздел «Пользователи».


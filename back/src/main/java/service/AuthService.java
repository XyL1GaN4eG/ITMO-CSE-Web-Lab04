package service;

import exceptions.DatabaseOperationException;
import exceptions.InvalidCredentialsException;
import exceptions.UnauthorizedException;
import exceptions.UserAlreadyExistsException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import model.User;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static util.PasswordUtil.hashPassword;
import static util.PasswordUtil.matchesPassword;

@Slf4j
public class AuthService {
    @Inject
    private UserRepository userRepository;

    public String login(String username, String password) throws UnauthorizedException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UnauthorizedException("Пользователя с таким никнеймом не существует");
        }
        log.info("Найден пользователь со следующими данными: {}", user);
        if (!matchesPassword(password, user.getPassword())) {
            throw new UnauthorizedException("Неверный логин или пароль");
        }
        if (user.getToken() == null || user.getTokenExpiration().isBefore(LocalDateTime.now()))
            user.setToken(UUID.randomUUID().toString());
        userRepository.updateTokenTime(user);
        return user.getToken();
    }

    public void registration(String username, String password) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        if (!isValidCredentialsException(username, password)) {
            throw new InvalidCredentialsException("Имя пользователя или пароль некорректны");
        }
        try {
            userRepository.save(
                    User.builder().username(username).password(hashPassword(password)).build()
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("Ошибка при сохранении пользователя в базу данных: ", e);
        }
    }

    //регуляркой проверять на корректность юзернейм и пароль
    private boolean isValidCredentialsException(String username, String password) {
        //todo: add regex
        return true;
    }
}

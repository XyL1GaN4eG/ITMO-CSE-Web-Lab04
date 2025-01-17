package web.service;

import jakarta.enterprise.context.ApplicationScoped;
import web.exceptions.DatabaseOperationException;
import web.exceptions.InvalidCredentialsException;
import web.exceptions.UnauthorizedException;
import web.exceptions.UserAlreadyExistsException;
import web.model.User;
import web.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import static web.util.PasswordUtil.hashPassword;
import static web.util.PasswordUtil.matchesPassword;

@Slf4j
@ApplicationScoped
public class AuthService {
    @Inject
    UserRepository userRepository;

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
    @SuppressWarnings("unused")
    private boolean isValidCredentialsException(String username, String password) {
        //todo: add regex
        return true;
    }
}

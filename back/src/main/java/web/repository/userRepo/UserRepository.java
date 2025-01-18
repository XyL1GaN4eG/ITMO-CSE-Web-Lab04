package web.repository.userRepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import web.exceptions.UnauthorizedException;
import web.model.User;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ApplicationScoped
@Slf4j
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        log.info("Попытка найти пользователя в бд с никнеймом: {}", username);
        return find("username", username).firstResult();
    }

    public void save(User user) {
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(10));
        persist(user);
        log.info("Время действия токена обновлено у пользователя: {}", user);
    }

    public User findByToken(String token) throws UnauthorizedException {
        var user = find("token", token).firstResult();
        if (user == null) throw new UnauthorizedException("Пользователь не найден по токену");

        //todo: вынести проверку действительности токена в отдельный метод/класс
        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) throw new UnauthorizedException(
                "Время действия токена истекло! Пожалуйста, войдите снова в свой аккаунт"
        );
        save(user);
        return user;
    }
}

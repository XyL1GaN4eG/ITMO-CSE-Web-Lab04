package web.repository.userRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import web.exceptions.UnauthorizedException;
import web.model.User;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ApplicationScoped
@Slf4j
public class UserRepository {
    @Inject
//    @PersistenceUnit(name = "users")
    EntityManager entityManager;

    public User findByUsername(String username) {
        log.info("Попытка найти пользователя в бд с никнеймом: {}", username);
        try {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Возвращаем null, если пользователь не найден
        }
    }

    public void update(User user) {
        entityManager.merge(user); // merge используется для обновления существующих записей
    }

    public void create(String username, String password) {
        var user = User.builder()
                .username(username)
                .password(password)
                .build();
        entityManager.persist(user); // persist используется для добавления новых записей
    }

    public boolean existsByUsername(String username) {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    public void save(User user) {
        if (user.getUserId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    public User findByToken(String token) throws UnauthorizedException {
        User user = entityManager
                .createQuery("SELECT u FROM User u WHERE u.token = :token", User.class)
                .setParameter("token", token)
                .getSingleResult();
        if (user == null) throw new UnauthorizedException("Пользователь не найден по токену");

        //todo: вынести проверку действительности токена в отдельный метод/класс
        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) throw new UnauthorizedException(
                "Время действия токена истекло! Пожалуйста, войдите снова в свой аккаунт"
        );
        updateTokenTime(user);
        return user;
    }

    public void updateTokenTime(User user) {
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(10));
        update(user);
        log.info("Время действия токена обновлено у пользователя: {}", user);
    }
}

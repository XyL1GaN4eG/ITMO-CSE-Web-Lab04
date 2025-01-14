package repository;

import exceptions.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.time.LocalDateTime;

@Transactional
@ApplicationScoped
@Slf4j
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

    @SuppressWarnings("unused")
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

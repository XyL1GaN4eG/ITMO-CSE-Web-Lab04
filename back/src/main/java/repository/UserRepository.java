package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Transactional // Автоматическое управление транзакциями
@ApplicationScoped
@Slf4j

public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Поиск пользователя по имени пользователя
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

    // Обновление пользователя
    public void update(User user) {
        entityManager.merge(user); // merge используется для обновления существующих записей
    }

    // Создание нового пользователя
    public void create(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        entityManager.persist(user); // persist используется для добавления новых записей
    }

    // Проверка существования пользователя по имени пользователя
    public boolean existsByUsername(String username) {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    // Сохранение или обновление пользователя
    public void save(User user) {
        if (user.getUserId() == null) {
            entityManager.persist(user); // Новый пользователь
        } else {
            entityManager.merge(user); // Обновление существующего пользователя
        }
    }

    // Поиск пользователя по токену
    public User findByToken(String token) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Возвращаем null, если пользователь не найден
        }
    }
}

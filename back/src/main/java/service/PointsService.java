package service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import exceptions.UnauthorizedException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import model.Point;
import repository.UserRepository;
import util.AreaChecker;
import config.MongoDBConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Stateless
public class PointsService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private AreaChecker areaChecker;

    private final MongoCollection<Point> pointsCollection = MongoDBConfig.getPointsCollection();

    public Point checkPoint(Point point, String token) {
        log.info("Начинается проверка точки: {}, с токеном: {}", point, token);
        var startTime = System.nanoTime();
        var attemptTime = System.currentTimeMillis();
        var user = userRepository.findByToken(token);
        if (user == null) {
            log.error("Не найден пользователь с токеном: {} ", token);
            throw new UnauthorizedException("Зарегистрируйтесь или войдите в систему!");
        }
        if (user.getTokenExpiration().isAfter(LocalDateTime.now())) {
            userRepository.updateTokenTime(user);
        }

        var isIn = areaChecker.check(
                point.getX(),
                point.getY(),
                point.getR()
        );
        log.debug("Результат проверки попадания в область: {}", isIn);

        point.setAttemptTime(attemptTime);
        point.setIn(isIn);
        point.setUserId(String.valueOf(user.getUserId()));
        point.setExecutionTime(System.nanoTime() - startTime);
        pointsCollection.insertOne(point);
        log.info("Точка успешно добавлена: {}", point);

        return point;
    }

    public List<Point> getAllPoints(String userId) {
        log.info("Получение всех точек для пользователя с идентификатором: {}", userId);
        var points = new ArrayList<Point>();
        pointsCollection.find(Filters.eq("userId", userId)).into(points);
        log.info("Найдено {} точек для пользователя {}", points.size(), userId);
        return points;
    }

    public void clear(String userId) {
        log.info("Удаление всех точек для пользователя с идентификатором: {}", userId);
        var deleteResult = pointsCollection.deleteMany(Filters.eq("userId", userId));
        log.info("Удалено {} точек для пользователя {}", deleteResult.getDeletedCount(), userId);
    }
}

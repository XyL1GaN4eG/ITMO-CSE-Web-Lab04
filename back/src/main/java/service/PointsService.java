package service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import exceptions.UnauthorizedException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import model.Point;
import model.User;
import repository.UserRepository;
import response.PointDTO;
import util.AreaChecker;
import config.MongoDBConfig;

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

    //todo: добавить проверку на уже существование этой точки в бд
    public PointDTO checkPoint(Point point, String token) throws UnauthorizedException {
        log.info("Начинается проверка точки: {}, с токеном: {}", point, token);
        var startTime = System.nanoTime();
        point.setAttemptTime(System.currentTimeMillis());
        var user = getUserByToken(token);

        var isIn = areaChecker.check(
                point.getX(),
                point.getY(),
                point.getR()
        );
        point.setIn(isIn);
        point.setUserId(String.valueOf(user.getUserId()));
        point.setExecutionTime(System.nanoTime() - startTime);
        pointsCollection.insertOne(point);
        return new PointDTO(point);
    }

    public List<PointDTO> getAllPoints(String token) throws UnauthorizedException {
        var userId = getUserByToken(token).getUserId().toString();
        log.info("Получение всех точек для пользователя с идентификатором: {}", userId);
        var points = new ArrayList<PointDTO>();
        pointsCollection.find(Filters.eq("userId", userId))
                .forEach(point -> points.add(new PointDTO(point)));
        log.info("Найдено {} точек для пользователя {}: {}", points.size(), userId, points);
        return points;
    }

    public void clear(String token) throws UnauthorizedException {
        var userId = getUserByToken(token).getUserId().toString();
        log.info("Удаление всех точек для пользователя с идентификатором: {}", userId);
        var countOfDeletedPoints = pointsCollection.deleteMany(Filters.eq("userId", userId)).getDeletedCount();
        log.info("Удалено {} точек для пользователя {}", countOfDeletedPoints, userId);
    }

    private User getUserByToken(String token) throws UnauthorizedException {
        var user = userRepository.findByToken(token);
        //todo: перенести эту логику в userRepo
        if (user == null) {
            log.error("Не найден пользователь с токеном: {} ", token);
            throw new UnauthorizedException("Зарегистрируйтесь или снова войдите в систему!");
        }
        return user;
    }
}

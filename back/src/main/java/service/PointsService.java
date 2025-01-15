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
import java.util.stream.Collectors;

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

    public List<List<PointDTO>> getAllPoints(String token) throws UnauthorizedException {
        var userId = getUserByToken(token).getUserId().toString();
        log.info("Получение всех точек для пользователя с идентификатором: {}", userId);

        // Получение всех точек пользователя
        var points = new ArrayList<Point>();
        pointsCollection.find(Filters.eq("userId", userId)).into(points);
        log.info("Найдено {} точек для пользователя {}: {}", points.size(), userId, points);

        // Группировка точек по значению R и преобразование в List<List<PointDTO>>
        var groupedPoints = points.stream()
                .map(PointDTO::new) // Преобразуем в DTO
                .collect(Collectors.groupingBy(PointDTO::getR)) // Группируем по R
                .values(); // Получаем коллекцию списков

        // Преобразуем Collection<List<PointDTO>> в List<List<PointDTO>>
        return new ArrayList<>(groupedPoints);
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

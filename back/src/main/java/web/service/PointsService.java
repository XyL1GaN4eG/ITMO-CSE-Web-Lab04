package web.service;

import jakarta.enterprise.context.ApplicationScoped;
import web.exceptions.UnauthorizedException;
import web.model.Point;
import web.model.User;
import web.repository.MongoRepository;
import web.repository.userRepo.UserRepository;
import web.response.PointDTO;
import web.util.AreaChecker;

import java.util.List;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@ApplicationScoped
public class PointsService {
    @Inject
    UserRepository userRepository;

    @Inject
    MongoRepository mongoRepository;

    @Inject
    AreaChecker areaChecker;

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
        mongoRepository.add(point);
        return new PointDTO(point);
    }

    public List<PointDTO> getAllPoints(String token) throws UnauthorizedException {
        var userId = getUserByToken(token).getUserId().toString();
        log.info("Получение всех точек для пользователя с идентификатором: {}", userId);
        var points = mongoRepository.getAll(userId);
        log.info("Найдено {} точек для пользователя {}: {}", points.size(), userId, points);
        return points;
    }

    public void clear(String token) throws UnauthorizedException {
        var userId = getUserByToken(token).getUserId().toString();
        mongoRepository.deleteByUserId(userId);
    }

    //todo: перенести эту логику в userRepo
    private User getUserByToken(String token) throws UnauthorizedException {
        var user = userRepository.findByToken(token);
        if (user == null) {
            log.error("Не найден пользователь с токеном: {} ", token);
            throw new UnauthorizedException("Зарегистрируйтесь или снова войдите в систему!");
        }
        return user;
    }
}

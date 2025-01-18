package web.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import web.config.MongoDBConfig;
import web.model.Point;
import web.response.PointDTO;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
public class MongoRepository {
    private final MongoCollection<Point> pointsCollection = MongoDBConfig.getPointsCollection();

    public ArrayList<PointDTO> getAllByUserId(String userId) {
        var points = new ArrayList<PointDTO>();
        pointsCollection.find(Filters.eq("userId", userId))
                .forEach(point -> points.add(new PointDTO(point)));
        return points;
    }

    public ArrayList<Point> getAll() {
        var points = new ArrayList<Point>();
        pointsCollection.find().forEach(points::add);
        return points;
    }

    public void add(Point point) {
        pointsCollection.insertOne(point);
    }

    public void deleteByUserId(String userId) {
        log.info("Удаление всех точек для пользователя с идентификатором: {}", userId);
        var countOfDeletedPoints = pointsCollection.deleteMany(Filters.eq("userId", userId)).getDeletedCount();
        log.info("Удалено {} точек для пользователя {}", countOfDeletedPoints, userId);
    }

    //todo: может сделать как то по красивее?
    public void deleteByPoint(Point point) {
        log.info("Удаление точки из MongoDB: {}", point);
        try {
            pointsCollection.deleteOne(Filters.and(
                    Filters.eq("_id", point.getId()),
                    Filters.eq("x", point.getX()),
                    Filters.eq("y", point.getY()),
                    Filters.eq("r", point.getR()),
                    Filters.eq("userId", point.getUserId()),
                    Filters.eq("executionTime", point.getExecutionTime()),
                    Filters.eq("attemptTime", point.getAttemptTime()),
                    Filters.eq("isIn", point.isIn())
            ));
            log.info("Точка успешно удалена: {}", point);
        } catch (Exception e) {
            log.error("Ошибка при удалении точки: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка при удалении точки", e);
        }
    }

}

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

    public ArrayList<PointDTO> getAll(String userId) {
        var points = new ArrayList<PointDTO>();
        pointsCollection.find(Filters.eq("userId", userId))
                .forEach(point -> points.add(new PointDTO(point)));
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
}

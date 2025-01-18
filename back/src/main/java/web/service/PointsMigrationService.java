package web.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import web.model.IntermediatePoint;
import web.model.Point;
import web.repository.MongoRepository;
import web.repository.archiveRepo.ArchivePointsRepository;
import web.repository.intermedRepo.IntermediatePointsRepository;

import java.time.Instant;

@Slf4j
@ApplicationScoped
public class PointsMigrationService {

    @Inject
    MongoRepository mongoRepository;

    @Inject
    IntermediatePointsRepository intermediatePointsRepository;

    @Inject
    ArchivePointsRepository archivePointsRepository;

    @Scheduled(every = "10s")
    public void startMigrating() {
        migrateFromMongoToPsql();
        migrateToArchive();
    }

    @Transactional
    public void migrateFromMongoToPsql() {
        log.debug("Начата проверка точек в MongoDB для миграции");
        var currentTime = Instant.now().toEpochMilli();

        for (var point : mongoRepository.getAll()) {
            var pointAge = currentTime - point.getAttemptTime();
            try {
                long TEN_MINUTES = 10 * 60 * 1000;
                if (pointAge < TEN_MINUTES) {
                    moveToIntermediateDB(point);
                    mongoRepository.delete(point);
                }
            } catch (Exception e) {
                log.error("Ошибка при регулярной задаче переноса точек из монго в промежуточную БД: ", e);
            }
        }
    }

    @Transactional
    public void migrateToArchive() {
        log.debug("Попытка перенести точки из промежуточной БД в архив");
        try {
            var arrayOfOldPoints = intermediatePointsRepository.findPointsByExpiration();
            intermediatePointsRepository.deleteArrayOfPoint(arrayOfOldPoints);
            archivePointsRepository.save(arrayOfOldPoints);
        } catch (Exception e) {
            log.error("Проблема при переносе точек в архивную базу данных: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void moveToIntermediateDB(Point point) {
        try {
            var intermediatePoint = pointToIntermediatePoint(point);
            intermediatePointsRepository.persist(intermediatePoint);
            log.info("Точка перемещена в промежуточную базу: {}", intermediatePoint);
        } catch (Exception e) {
            log.error("Проблема при переносе точки в промежуточную БД: {}", e.getMessage(), e);
        }
    }

    // Преобразование данных из Point в IntermediatePoint
    private static IntermediatePoint pointToIntermediatePoint(Point point) {
        var intermediatePoint = new IntermediatePoint();
        intermediatePoint.setUserId(Long.parseLong(point.getUserId()));
        intermediatePoint.setX(point.getX());
        intermediatePoint.setY(point.getY());
        intermediatePoint.setR(point.getR());
        intermediatePoint.setAttemptTime(point.getAttemptTime());
        intermediatePoint.setIn(point.isIn());
        return intermediatePoint;
    }
}

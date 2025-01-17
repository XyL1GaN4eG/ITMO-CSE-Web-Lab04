package web.service;
//
//import io.quarkus.scheduler.Scheduled;
//import jakarta.annotation.PostConstruct;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import web.model.IntermediatePoint;
//import web.model.Point;
//import web.repository.MongoRepository;
//import web.repository.archiveRepo.ArchivePointsRepository;
//import web.repository.intermedRepo.IntermediatePointsRepository;
//
//import java.time.Instant;
//
//@Slf4j
//@ApplicationScoped
public class PointsMigrationService {
//    @Inject
//    MongoRepository mongoRepository;
//    @Inject
//    IntermediatePointsRepository intermediatePointsRepository;
//    @Inject
//    ArchivePointsRepository archivePointsRepository;
//
//    private final long tenMinutes = 10 * 60 * 1000;
//
//    @PostConstruct
//    private void init() {
//        log.info("PointsMigrationService начал свою работу");
//    }
//
//    @Scheduled(cron = "* * * * * ?")
//    @Transactional
//    public void scheduledProcessPointsFromMongoToIntermediate() {
//        log.info("Начата проверка точек в MongoDB для миграции");
//        var currentTime = Instant.now().toEpochMilli();
//
//        for (var point : mongoRepository.getAll()) {
//            var pointAge = currentTime - point.getAttemptTime();
//            try {
//                if (pointAge < tenMinutes) {
//                    moveToIntermediateDB(point);
//                    mongoRepository.deleteByPoint(point);
//                }
//                //todo: нормальная обработка ошибок
//            } catch (Exception e) {
//                log.error("Ошибка при регулярной задаче переноса точек из монго в промежуточную БД: ", e);
//            }
//        }
//    }
//
////    @Transactional
//    private void moveToIntermediateDB(Point point) {
//        try {
//            var intermediatePoint = new IntermediatePoint();
//            //todo: вынести приведение Point к intermediatepoint куда нибудь
//            intermediatePoint.setUserId(Long.parseLong(point.getUserId()));
//            intermediatePoint.setX(point.getX());
//            intermediatePoint.setY(point.getY());
//            intermediatePoint.setR(point.getR());
//            intermediatePoint.setAttemptTime(point.getAttemptTime());
//            intermediatePoint.setIn(point.isIn());
//
//            intermediatePointsRepository.save(intermediatePoint);
//            log.info("Точка перемещена в промежуточную базу: {}", intermediatePoint);
//        } catch (Exception e) {
//            log.error("Проблема при переносе точки в промежуточну бд: {}", e.getMessage(), e);
//        }
//    }
//
//    //todo: возможно стоит объединить этот метод с scheduledProcessPointsFromMongoToIntermediate
//    // чтобы они вместе переносились
//    @Scheduled(every = "10s")
//    @Transactional
//    public void scheduledProcessPointsFromIntermediateToArchive() {
//        log.info("Попытка перенести точки из промежуточной бд в архив");
//        try {
//            var arrayOfOldPoints = intermediatePointsRepository.findPointsByExpiration();
//            intermediatePointsRepository.deleteArrayOfPoint(arrayOfOldPoints);
//            archivePointsRepository.save(arrayOfOldPoints);
//        } catch (Exception e) {
//            log.error("Проблема при переносе точек в архивную базу данных: {}", e.getMessage(), e);
//        }
//    }
}

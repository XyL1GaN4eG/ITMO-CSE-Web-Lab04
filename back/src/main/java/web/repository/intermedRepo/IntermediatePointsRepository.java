package web.repository.intermedRepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import web.model.IntermediatePoint;

import java.util.List;

@Slf4j
@ApplicationScoped
public class IntermediatePointsRepository implements PanacheRepository<IntermediatePoint> {

    private static final long EXPIRATION_TIME = 3 * 60 * 1000; // 3 минуты в миллисекундах

    public List<IntermediatePoint> findPointsByExpiration() {
        log.info("Попытка найти список точек старше {} минут в промежуточной БД", EXPIRATION_TIME / 1000 / 60);
        long expirationTime = System.currentTimeMillis() - EXPIRATION_TIME;
        List<IntermediatePoint> resultList = find("attemptTime < ?1", expirationTime).list();
        log.info("Получили список точек старше {} минут в промежуточной БД: {}", EXPIRATION_TIME / 1000 / 60, resultList);
        return resultList;
    }



    public void deleteArrayOfPoint(List<IntermediatePoint> points) {
        log.info("Попытка удалить следующие точки из промежуточной бд: {}", points);
        // удаляем все точки, используя условие по id
        delete("id in ?1", points.stream().map(IntermediatePoint::getId).toList());
    }


}

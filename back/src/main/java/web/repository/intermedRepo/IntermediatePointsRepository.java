package web.repository.intermedRepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import web.model.IntermediatePoint;

import java.util.List;

@Slf4j
@ApplicationScoped
public class IntermediatePointsRepository implements PanacheRepository<IntermediatePoint> {

    //todo: либо нормальный нейминг, либо разнести на два метода, а то чето принцип единой ответственности не соблюдается
    public List<IntermediatePoint> findPointsByExpiration() {
        log.info("Попытка найти список точек старше трех минут в промежуточной БД");
        long expirationTime = System.currentTimeMillis() - 3 * 60 * 1000; // 3 минуты назад
        List<IntermediatePoint> resultList = find("attemptTime < ?1", expirationTime).list();
        log.info("Получили список точек старше трех минут в промежуточной БД: {}", resultList);
        return resultList;
    }


    public void deleteArrayOfPoint(List<IntermediatePoint> points) {
        log.info("Попытка удалить следующие точки из промежуточной бд: {}", points);
        //todo: возможно это можно переписать более лаконично используя паначи
        for (IntermediatePoint point : points) {
            delete(point);
        }
    }

}

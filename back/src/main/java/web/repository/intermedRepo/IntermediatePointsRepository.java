package web.repository.intermedRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import web.model.IntermediatePoint;

import java.util.List;

//@Transactional
//@ApplicationScoped
////todo: add some logs
//@Slf4j
public class IntermediatePointsRepository {
//    @Inject
//    EntityManager entityManager;
//
//    public void save(IntermediatePoint point) {
//        log.info("Попытка сохранить в промежуточную бд точку: {}", point);
//        entityManager.persist(point);
//    }
//
//    public void delete(IntermediatePoint point) {
//        log.info("Попытка удалить в промежуточной бд точку: {}", point);
//        entityManager.remove(point);
//    }
//
//    //todo: либо нормальный нейминг, либо разнести на два метода, а то чето принцип единой ответственности не соблюдается
//    public List<IntermediatePoint> findPointsByExpiration() {
//        log.info("Попытка найти список точек старше трех минут в промежуточной бд");
//        String hql = "SELECT ip FROM IntermediatePoint ip WHERE ip.attemptTime < :time";
//        var resultList = entityManager.createQuery(hql, IntermediatePoint.class)
//                .setParameter("time", System.currentTimeMillis() - 3 * 60 * 1000) // 3 минуты
//                .getResultList();
//        log.info("Получили список точек старше трех минут в промежуточной бд: {}", resultList);
//        return resultList;
//    }
//
//
//    public void deleteArrayOfPoint(List<IntermediatePoint> points) {
//        log.info("Попытка удалить следующие точки из промежуточной бд: {}", points);
//        for (IntermediatePoint point : points) {
//            entityManager.remove(point);
//        }
//    }

}

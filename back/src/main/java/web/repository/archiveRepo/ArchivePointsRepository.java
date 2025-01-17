package web.repository.archiveRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import web.model.ArchivePoint;
import web.model.IntermediatePoint;

import java.util.List;

//@Transactional
//@ApplicationScoped
public class ArchivePointsRepository {
//    @Inject
//    EntityManager entityManager;
//
//    public void save(ArchivePoint point) {
//        entityManager.persist(point);
//    }
//
//    public void save(List<IntermediatePoint> array) {
//        for (IntermediatePoint intermediatePoint : array) {
//            save(new ArchivePoint(intermediatePoint));
//        }
//    }
}

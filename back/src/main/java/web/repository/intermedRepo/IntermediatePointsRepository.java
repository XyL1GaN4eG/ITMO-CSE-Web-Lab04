package web.repository.intermedRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import web.model.IntermediatePoint;

@Transactional
@ApplicationScoped
public class IntermediatePointsRepository {
    @Inject
    EntityManager entityManager;

    public void save(IntermediatePoint point) {
        entityManager.persist(point);
    }
}

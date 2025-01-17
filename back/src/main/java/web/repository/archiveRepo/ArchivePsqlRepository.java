package web.repository.archiveRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import web.model.ArchivePoint;

@Transactional
@ApplicationScoped
public class ArchivePsqlRepository {
    @Inject
    EntityManager entityManager;

    public void save(ArchivePoint point) {
        entityManager.persist(point);
    }
}

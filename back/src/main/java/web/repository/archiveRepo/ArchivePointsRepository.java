package web.repository.archiveRepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import web.model.ArchivePoint;
import web.model.IntermediatePoint;

import java.util.List;

@Transactional
@ApplicationScoped
public class ArchivePointsRepository implements PanacheRepository<ArchivePoint> {
    public void save(List<IntermediatePoint> array) {
        for (IntermediatePoint intermediatePoint : array) {
            persist(new ArchivePoint(intermediatePoint));
        }
    }
}

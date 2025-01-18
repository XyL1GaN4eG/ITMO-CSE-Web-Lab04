package web.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "archivepoints")
@Data
@NoArgsConstructor
public class ArchivePoint extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double x;
    private double y;
    private double r;
    private long attemptTime;
    private long executionTime;
    private boolean isIn;

    public ArchivePoint(IntermediatePoint intermediatePoint) {
        this.userId = intermediatePoint.getUserId();
        this.attemptTime = intermediatePoint.getAttemptTime();
        this.x = intermediatePoint.getX();
        this.y = intermediatePoint.getY();
        this.r = intermediatePoint.getR();
        this.isIn = intermediatePoint.isIn();
        this.executionTime = intermediatePoint.getExecutionTime();
    }
}

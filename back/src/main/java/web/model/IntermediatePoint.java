package web.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "intermediatepoints")
@Data
public class IntermediatePoint extends PanacheEntityBase {
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
}

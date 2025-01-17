package web.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "intermediatepoints")
@Data
public class IntermediatePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double x;
    private double y;
    private double r;
    private long attemptTime;
    private boolean isIn;
}

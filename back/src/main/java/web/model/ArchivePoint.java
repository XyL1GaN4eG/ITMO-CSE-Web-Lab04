package web.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "archivepoints")
@Data
public class ArchivePoint {
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

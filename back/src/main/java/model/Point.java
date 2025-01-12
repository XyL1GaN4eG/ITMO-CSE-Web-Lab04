package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Point {
    @Id
    private Long pointId;
    private Long userId;
    private double x;
    private double y;
    private double r;
    private boolean isIn;
    private long attemptTime;
    private double executionTime;
}

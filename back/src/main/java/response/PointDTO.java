package response;

import lombok.Data;
import model.Point;

@Data
public final class PointDTO {
    private final double x;
    private final double y;
    private final double r;
    private final long executionTime;
    private final long attemptTime;
    private final boolean isIn;
    public PointDTO(Point point) {
        this.x= point.getX();
        this.y = point.getY();
        this.r = point.getR();
        this.isIn = point.isIn();
        this.executionTime = point.getExecutionTime();
        this.attemptTime = point.getAttemptTime();
    }
}

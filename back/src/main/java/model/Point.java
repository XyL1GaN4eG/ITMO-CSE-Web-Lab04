package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
public class Point {
    @BsonId
    private String id;
    @BsonProperty("x")
    private double x;
    @BsonProperty("y")
    private double y;
    @BsonProperty("r")
    private double r;
    @BsonProperty("userId")
    private String userId;
    @BsonProperty("executionTime")
    private long executionTime;
    @BsonProperty("attemptTime")
    private long attemptTime;
    @BsonProperty()
    private boolean isIn;
}

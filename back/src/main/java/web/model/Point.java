package web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Point {
    @BsonId
    private ObjectId id;
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
    @BsonProperty("isIn")
    private boolean isIn;
}

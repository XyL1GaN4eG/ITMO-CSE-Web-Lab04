package model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
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
    @BsonProperty()
    private boolean isIn;
    public Point() {
    } // Обязательный пустой конструктор

}

package config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import model.Point;

@Slf4j
public class MongoDBConfig {
    private static final String MONGO_DB_URL = "mongodb://localhost:27017";
    private static final String DB_NAME = "points";
    private static MongoClient mongoClient;

    static {
        try {
            mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyConnectionString(new com.mongodb.ConnectionString(MONGO_DB_URL))
                            .build()
            );
            log.info("Подключение к MongoDB установлено.");
        } catch (Exception e) {
            log.error("Ошибка подключения к MongoDB: {}", e.getMessage(), e);
        }
    }

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DB_NAME);
    }

    public static MongoCollection<Point> getPointsCollection() {
        return getDatabase().getCollection("points", Point.class);
    }
}

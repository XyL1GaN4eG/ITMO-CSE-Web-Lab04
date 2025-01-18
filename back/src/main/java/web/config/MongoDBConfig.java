package web.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import web.model.Point;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBConfig {
    private static final Logger log = LoggerFactory.getLogger(MongoDBConfig.class);
    private static final String MONGO_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "points";

    private static final CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );

    private static final MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(MONGO_URL))
                    .codecRegistry(pojoCodecRegistry)
                    .build()
    );

    static {
        log.info("MongoDB конфигурация настроена с кодеками POJO.");
    }

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static MongoCollection<Point> getPointsCollection() {
        return getDatabase().getCollection("points", Point.class);
    }
}

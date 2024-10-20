package de.hbrs.ia.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {
    private final static String MONGO_URL = "mongodb://localhost:27017";
    private final static String DATABASE_NAME = "highPerformance";
    private final static String SALESMEN_COLLECTION_NAME = "salesmen";
    private final static String SOCIAL_PERFORMANCE_COLLECTION_NAME = "socialPerformanceRatings";

    private final MongoClient client;
    private final MongoDatabase db;
    private final MongoCollection<Document> salesmenCollection;
    private final MongoCollection<Document> socialPerformanceCollection;
    private static MongoConnection instance;

    private MongoConnection() {
        try {
            this.client = MongoClients.create(MONGO_URL);
            this.db = this.client.getDatabase(DATABASE_NAME);
            this.salesmenCollection = this.db.getCollection(SALESMEN_COLLECTION_NAME);
            this.socialPerformanceCollection = this.db.getCollection(SOCIAL_PERFORMANCE_COLLECTION_NAME);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to connect to MongoDB");
        }
    }
    public MongoDatabase getDatabase(){
        return db;
    }

    public MongoCollection<Document> getSalesmenCollection(){
        return salesmenCollection;
    }

    public MongoCollection<Document> getSocialPerformanceCollection(){
        return socialPerformanceCollection;
    }

    public MongoClient getClient(){
        return client;
    }

    public void close(){
        this.client.close();
    }

    public static MongoConnection getInstance(){
        if (instance == null)
            instance = new MongoConnection();
        return instance;
    }
}

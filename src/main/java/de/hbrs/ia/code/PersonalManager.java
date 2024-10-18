package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import de.hbrs.ia.db.MongoConnection;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.bson.Document;

public class PersonalManager implements ManagePersonal{
    private final MongoConnection mongo = MongoConnection.getInstance();
    private static PersonalManager instance;

    @Override
    public void createSalesMan(SalesMan record) throws IllegalArgumentException{
        Document result = mongo.getSalesmenCollection().find(eq("sid", record.getId())).first();
        if (result != null){
            throw new IllegalArgumentException("A salesman entry with this ID already exists.");
        }
        mongo.getSalesmenCollection().insertOne(record.toDocument());
    }

    @Override
    public SalesMan readSalesMan(int sid) throws NoSuchElementException{
        Document result = mongo.getSalesmenCollection()
            .find(eq("sid", sid)).first();
        if(result == null)
            throw new NoSuchElementException("There is no salesman entry with this ID.");
        return SalesMan.fromDocument(result);
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = mongo.getSalesmenCollection().find().iterator()) {
            while (cursor.hasNext())
                list.add(SalesMan.fromDocument(cursor.next()));
        }
        return list;
    }

    @Override
    public void updateSalesMan(SalesMan record) throws NoSuchElementException{
        UpdateResult result = mongo.getSalesmenCollection().updateOne(eq("sid", record.getId()), record.toDocument());
        if (result.getMatchedCount() == 0)
            throw new NoSuchElementException("No salesman entry for this ID has been found.");
    }

    @Override
    public void deleteSalesMan(SalesMan record) throws NoSuchElementException{
        DeleteResult result = mongo.getSalesmenCollection().deleteOne(eq("sid", record.getId()));
        if (result.getDeletedCount() == 0)
            throw new NoSuchElementException("There is no matching salesman entry for this ID.");
        readSocialPerformanceRecord(record).forEach(this::deleteSocialPerformanceRecord);
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) throws NoSuchElementException{
        salesMan.getGoalIDs().add(record.getGid());
        updateSalesMan(salesMan);
        Document result = mongo.getSocialPerformanceCollection().find(eq("gid", record.getGid())).first();
        if (result != null){
            throw new IllegalArgumentException("A social-performance-record with this ID already exists.");
        }
        mongo.getSocialPerformanceCollection().insertOne(record.toDocument());
    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        List<SocialPerformanceRecord> list = new ArrayList<>();
        salesMan.getGoalIDs().forEach(gid -> {
            Document doc = mongo.getSalesmenCollection().find(eq("gid", gid)).first();
            if (doc == null)
                return;
            list.add(SocialPerformanceRecord.fromDocument(doc));
        });
        return list;
    }

    @Override
    public void deleteSocialPerformanceRecord(SocialPerformanceRecord record) throws NoSuchElementException{
        DeleteResult result = mongo.getSalesmenCollection().deleteOne(eq("gid", record.getGid()));
        if (result.getDeletedCount() == 0)
            throw new NoSuchElementException("There is no matching social-performance-record for this ID.");
    }

    public static PersonalManager getInstance(){
        if(instance == null)
            instance = new PersonalManager();
        return instance;
    }
}

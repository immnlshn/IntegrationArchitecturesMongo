package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;

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
    // Singleton
    private static PersonalManager instance;

    // Private constructor to prevent instantiation
    private PersonalManager() {}

    @Override
    public void createSalesMan(SalesMan record) throws IllegalArgumentException{
        // Check if a Salesman with the same Id already exists
        Document result = mongo.getSalesmenCollection().find(eq("sid", record.getId())).first();
        if (result != null){
            // throw an exception if a Salesman with the same Id already exists
            throw new IllegalArgumentException("A salesman with this ID already exists.");
        }
        // Insert the Salesman into the database
        mongo.getSalesmenCollection().insertOne(record.toDocument());
    }

    @Override
    public SalesMan readSalesMan(int sid) throws NoSuchElementException{
        // Find the Salesman with the given Id
        Document result = mongo.getSalesmenCollection()
            .find(eq("sid", sid)).first();
        if(result == null)
            // Throw an exception if no Salesman with the given Id exists
            throw new NoSuchElementException("There is no salesman with this ID stored in the database.");
        // Deserialize the Salesman from the Document and return it
        return SalesMan.fromDocument(result);
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> list = new ArrayList<>();
        // try with resources to automatically close the cursor
        try (MongoCursor<Document> cursor = mongo.getSalesmenCollection().find().iterator()) {
            // Iterate over all Salesmen and add them to the list
            while (cursor.hasNext())
                list.add(SalesMan.fromDocument(cursor.next()));
        }
        return list;
    }

    @Override
    public void updateSalesMan(SalesMan record) throws NoSuchElementException{
        // Update the Salesman with the given Object
        UpdateResult result = mongo.getSalesmenCollection().updateOne(eq("sid", record.getId()), new Document("$set", record.toDocument()));
        if (result.getMatchedCount() == 0)
            // Throw an exception if no Salesman was updated
            throw new NoSuchElementException("There is no salesman with this ID stored in the database.");
    }

    @Override
    public void deleteSalesMan(SalesMan record) throws NoSuchElementException{
        // Delete all SocialPerformanceRecords of the Salesman
        readSocialPerformanceRecord(record).forEach(goal -> deleteSocialPerformanceRecord(goal, record));
        // Delete the Salesman
        DeleteResult result = mongo.getSalesmenCollection().deleteOne(eq("sid", record.getId()));
        if (result.getDeletedCount() == 0)
            // Throw an exception if no Salesman was deleted
            throw new NoSuchElementException("There is no salesman with this ID stored in the database.");
    }

    @Override
    public void addSocialPerformanceRecord(SalesMan salesMan, SocialPerformanceRecord record) throws NoSuchElementException{
        // Add the goal to the Salesman object
        salesMan.getGoalIDs().add(record.getGid());
        // Check if a goal with the same Id already exists
        Document result = mongo.getSocialPerformanceCollection().find(eq("gid", record.getGid())).first();
        if (result != null){
            throw new IllegalArgumentException("A goal with this ID already exists.");
        }
        // Update the Salesman
        updateSalesMan(salesMan);
        // Insert the goal into the database
        mongo.getSocialPerformanceCollection().insertOne(record.toDocument());
    }


    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        List<SocialPerformanceRecord> list = new ArrayList<>();
        // Iterate over all goals of the Salesman and add them to the list
        salesMan.getGoalIDs().forEach(gid -> {
            Document doc = mongo.getSocialPerformanceCollection().find(eq("gid", gid)).first();
            // Skip if the goal does not exist
            if (doc == null)
                return;
            list.add(SocialPerformanceRecord.fromDocument(doc));
        });
        return list;
    }

    @Override
    public SocialPerformanceRecord readSocialPerformanceRecord(int gid) throws NoSuchElementException{
        // Find the goal with the given Id
        Document result = mongo.getSocialPerformanceCollection().find(eq("gid", gid)).first();
        if(result == null)
            // Throw an exception if no goal with the given Id exists
            throw new NoSuchElementException("There is no goal with this ID stored in the database.");
        // Deserialize the goal from the Document and return it
        return SocialPerformanceRecord.fromDocument(result);
    }

    @Override
    public void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesman) throws NoSuchElementException{
        // Check if the Salesman has the goal
        if (!salesman.getGoalIDs().contains(record.getGid()))
            throw new NoSuchElementException("There is no goal with this ID stored in the database.");
        // Remove the goal from the Salesman object
        salesman.getGoalIDs().remove(record.getGid());
        // Delete the goal from the database
        DeleteResult result = mongo.getSocialPerformanceCollection().deleteOne(eq("gid", record.getGid()));
        if (result.getDeletedCount() == 0)
            // Throw an exception if no goal was deleted
            throw new NoSuchElementException("There is no goal with this ID stored in the database.");
        // Update the Salesman
        updateSalesMan(salesman);
    }

    // Singleton
    public static PersonalManager getInstance(){
        if(instance == null)
            instance = new PersonalManager();
        return instance;
    }
}

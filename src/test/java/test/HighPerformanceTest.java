package test;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.hbrs.ia.code.ManagePersonal;
import de.hbrs.ia.code.PersonalManager;
import de.hbrs.ia.db.MongoConnection;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import java.util.NoSuchElementException;
import org.bson.Document;
import org.junit.jupiter.api.*;
import java.util.List;

class HighPerformanceTest {
    // Test Mongo Connection
    final String MONGO_URL = "mongodb://localhost:27017";
    final String DATABASE_NAME = "highPerformance";
    final String SALESMEN_COLLECTION_NAME = "salesmen";
    final String SOCIAL_PERFORMANCE_COLLECTION_NAME = "socialPerformanceRatings";

    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> salesmenCollection;
    MongoCollection<Document> socialPerformanceCollection;

    ManagePersonal manager;

    SalesMan salesMan;
    SocialPerformanceRecord socialPerformanceRecord;
    SocialPerformanceRecord socialPerformanceRecord2;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up");

        // Create connection to MongoDB
        client = MongoClients.create(MONGO_URL);
        db = client.getDatabase(DATABASE_NAME);
        salesmenCollection = db.getCollection(SALESMEN_COLLECTION_NAME);
        socialPerformanceCollection = db.getCollection(SOCIAL_PERFORMANCE_COLLECTION_NAME);

        manager = PersonalManager.getInstance();

        // Create test data
        salesMan = new SalesMan(1, "Max", "Mustermann");
        socialPerformanceRecord = new SocialPerformanceRecord(1, "Description", 100, 50, 2021);
        socialPerformanceRecord2 = new SocialPerformanceRecord(2, "Description2", 100, 50, 2021);
    }

    @AfterEach
    void close() {
        System.out.println("Closing");

        // Clean up test data
        MongoConnection.getInstance().dropDatabase();
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Tearing down");

        // Close connection to MongoDB
        MongoConnection.getInstance().close();
    }

    @Test
    void insertSalesMan(){
        System.out.println("Running insertSalesMan");
        manager.createSalesMan(salesMan);

        // Check if SalesMan was inserted
        assertEquals(1, salesmenCollection.countDocuments());

        // Check if SalesMan was inserted correctly
        SalesMan check = manager.readSalesMan(1);
        assertEquals(check, salesMan);

        // Check if SalesMan is in the list of all SalesMen
        List<SalesMan> checkList = manager.readAllSalesMen();
        assertEquals(checkList.get(0), salesMan);
    }

    @Test
    void falseInsertSalesMan(){
        System.out.println("Running falseInsertSalesMan");
        // Insert SalesMan
        manager.createSalesMan(salesMan);

        // Insert SalesMan with same ID
        assertThrows(IllegalArgumentException.class, () -> manager.createSalesMan(salesMan));
    }

    @Test
    void readSalesMan() {
        System.out.println("Running readSalesMan");
        // Insert SalesMan
        manager.createSalesMan(salesMan);

        // Read SalesMan and check if it is the same
        SalesMan check = manager.readSalesMan(1);
        assertEquals(check, salesMan);
    }

    @Test
    void falseReadSalesMan() {
        System.out.println("Running falseReadSalesMan");
        // Try to read SalesMan that does not exist
        assertThrows(NoSuchElementException.class, () -> manager.readSalesMan(1));
    }

    @Test
    void addSocialPerformanceRecord() {
        System.out.println("Running addSocialPerformanceRecord");

        // Insert SalesMan and add goal
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);

        // Check if goal was inserted
        assertEquals(1, socialPerformanceCollection.countDocuments());

        // Check if goal was inserted correctly
        SocialPerformanceRecord check = manager.readSocialPerformanceRecord(1);
        assertEquals(check, socialPerformanceRecord);

        /* Check if goal is in the list of all goals
            * of the SalesMan
         */
        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(checkList.get(0), socialPerformanceRecord);
    }

    @Test
    void falseAddSocialPerformanceRecord() {
        System.out.println("Running falseAddSocialPerformanceRecord");

        // Try to add SocialPerformanceRecord to SalesMan that does not exist
        assertThrows(NoSuchElementException.class, () -> manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord));
    }

    @Test
    void deleteSalesMan() {
        System.out.println("Running deleteSalesMan");
        // Insert SalesMan and delete it
        manager.createSalesMan(salesMan);
        manager.deleteSalesMan(salesMan);

        // Check if SalesMan was deleted
        assertEquals(0, salesmenCollection.countDocuments());
    }

    @Test
    void deleteSalesManWithGoals() {
        System.out.println("Running deleteSalesManWithGoals");

        // Insert SalesMan, add goal and delete SalesMan
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.deleteSalesMan(salesMan);

        // Check if SalesMan and goal were deleted
        assertEquals(0, salesmenCollection.countDocuments());
        assertEquals(0, socialPerformanceCollection.countDocuments());
    }

    @Test
    void falseDeleteSalesMan() {
        System.out.println("Running falseDeleteSalesMan");

        // Try to delete SalesMan that does not exist
        assertThrows(NoSuchElementException.class, () -> manager.deleteSalesMan(salesMan));
    }

    @Test
    void deleteSocialPerformanceRecord() {
        System.out.println("Running deleteSocialPerformanceRecord");

        // Insert SalesMan, add goal, delete goal and check if it was deleted
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.deleteSocialPerformanceRecord(socialPerformanceRecord, salesMan);

        // Check if goal was deleted
        assertEquals(0, socialPerformanceCollection.countDocuments());

        // Check if goal is not in the list of all goals
        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(0, checkList.size());
    }

    @Test
    void falseDeleteSocialPerformanceRecord() {
        System.out.println("Running falseDeleteSocialPerformanceRecord");

        // Try to delete goal that does not exist
        assertThrows(NoSuchElementException.class, () -> manager.deleteSocialPerformanceRecord(socialPerformanceRecord, salesMan));
    }

    @Test
    void falseReadSocialPerformanceRecord() {
        System.out.println("Running falseReadSocialPerformanceRecord");


        assertThrows(NoSuchElementException.class, () -> manager.readSocialPerformanceRecord(1));
    }

    @Test
    void falseUpdateSalesMan() {
        System.out.println("Running falseUpdateSalesMan");

        // Try to update SalesMan that does not exist
        assertThrows(NoSuchElementException.class, () -> manager.updateSalesMan(salesMan));
    }

    @Test
    void readAllSalesMen() {
        System.out.println("Running readAllSalesMen");

        // Insert SalesMan and get list of all SalesMen
        manager.createSalesMan(salesMan);
        List<SalesMan> checkList = manager.readAllSalesMen();

        // Check if SalesMan is in the list
        assertEquals(checkList.get(0), salesMan);
    }

    @Test
    void readSocialPerformanceRecord() {
        System.out.println("Running readSocialPerformanceRecord");

        // Insert SalesMan and add goals
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord2);

        // get list of goals
        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);

        // Check if goals are in the list
        assertEquals(checkList.get(0), socialPerformanceRecord);
        assertEquals(checkList.get(1), socialPerformanceRecord2);
    }
}

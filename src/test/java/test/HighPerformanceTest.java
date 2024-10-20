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
        client = MongoClients.create(MONGO_URL);
        db = client.getDatabase(DATABASE_NAME);
        salesmenCollection = db.getCollection(SALESMEN_COLLECTION_NAME);
        socialPerformanceCollection = db.getCollection(SOCIAL_PERFORMANCE_COLLECTION_NAME);
        manager = PersonalManager.getInstance();

        salesMan = new SalesMan(1, "Max", "Mustermann");
        socialPerformanceRecord = new SocialPerformanceRecord(1, "Description", 100, 50, 2021);
        socialPerformanceRecord2 = new SocialPerformanceRecord(2, "Description2", 100, 50, 2021);
    }

    @AfterEach
    void close() {
        System.out.println("Closing");
        client.close();
        client = null;
        MongoConnection.getInstance().dropDatabase();
        MongoConnection.getInstance().close();
    }

    @Test
    void insertSalesMan(){
        System.out.println("Running insertSalesMan");
        manager.createSalesMan(salesMan);
        assertEquals(1, salesmenCollection.countDocuments());

        SalesMan check = manager.readSalesMan(1);
        assertEquals(check, salesMan);

        List<SalesMan> checkList = manager.readAllSalesMen();
        assertEquals(checkList.get(0), salesMan);
    }

    @Test
    void falseInsertSalesMan(){
        System.out.println("Running falseInsertSalesMan");
        manager.createSalesMan(salesMan);
        assertThrows(IllegalArgumentException.class, () -> manager.createSalesMan(salesMan));
    }

    @Test
    void readSalesMan() {
        System.out.println("Running readSalesMan");
        manager.createSalesMan(salesMan);
        SalesMan check = manager.readSalesMan(1);
        assertEquals(check, salesMan);
    }

    @Test
    void falseReadSalesMan() {
        System.out.println("Running falseReadSalesMan");
        assertThrows(NoSuchElementException.class, () -> manager.readSalesMan(1));
    }

    @Test
    void addSocialPerformanceRecord() {
        System.out.println("Running addSocialPerformanceRecord");
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        assertEquals(1, socialPerformanceCollection.countDocuments());

        SocialPerformanceRecord check = manager.readSocialPerformanceRecord(1);
        assertEquals(check, socialPerformanceRecord);

        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(checkList.get(0), socialPerformanceRecord);
    }

    @Test
    void falseAddSocialPerformanceRecord() {
        System.out.println("Running falseAddSocialPerformanceRecord");
        assertThrows(NoSuchElementException.class, () -> manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord));
    }

    @Test
    void deleteSalesMan() {
        System.out.println("Running deleteSalesMan");
        manager.createSalesMan(salesMan);
        manager.deleteSalesMan(salesMan);
        assertEquals(0, salesmenCollection.countDocuments());
    }

    @Test
    void deleteSalesManWithGoals() {
        System.out.println("Running deleteSalesManWithGoals");
        SalesMan salesMan = new SalesMan(1, "Max", "Mustermann");
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.deleteSalesMan(salesMan);
        assertEquals(0, salesmenCollection.countDocuments());
        assertEquals(0, socialPerformanceCollection.countDocuments());

        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(0, checkList.size());
    }

    @Test
    void falseDeleteSalesMan() {
        System.out.println("Running falseDeleteSalesMan");
        assertThrows(NoSuchElementException.class, () -> manager.deleteSalesMan(salesMan));
    }

    @Test
    void deleteSocialPerformanceRecord() {
        System.out.println("Running deleteSocialPerformanceRecord");
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.deleteSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        assertEquals(0, socialPerformanceCollection.countDocuments());

        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(0, checkList.size());
    }

    @Test
    void falseDeleteSocialPerformanceRecord() {
        System.out.println("Running falseDeleteSocialPerformanceRecord");
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
        assertThrows(NoSuchElementException.class, () -> manager.updateSalesMan(salesMan));
    }

    @Test
    void readAllSalesMen() {
        System.out.println("Running readAllSalesMen");
        manager.createSalesMan(salesMan);
        List<SalesMan> checkList = manager.readAllSalesMen();
        assertEquals(checkList.get(0), salesMan);
    }

    @Test
    void readSocialPerformanceRecord() {
        System.out.println("Running readSocialPerformanceRecord");
        manager.createSalesMan(salesMan);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord);
        manager.addSocialPerformanceRecord(salesMan, socialPerformanceRecord2);

        List<SocialPerformanceRecord> checkList = manager.readSocialPerformanceRecord(salesMan);
        assertEquals(checkList.get(0), socialPerformanceRecord);
        assertEquals(checkList.get(1), socialPerformanceRecord2);
    }
}

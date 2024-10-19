package test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.hbrs.ia.db.MongoConnection;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.bson.Document;
import org.junit.jupiter.api.*;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HighPerformanceTest {

   /* MongoConnection conn;

    SalesMan testPerson = new SalesMan("Sascha","Alda",90133);
    SalesMan wrongPerson = new SalesMan("John","Doe",91111);

    SocialPerformanceRecord testRecord = new SocialPerformanceRecord("90909","test",9,9,2000);

    SocialPerformanceRecord wrondRecord = new SocialPerformanceRecord("90910","test2",10,10,2010);
    ManagePersonalImpl testClass = new ManagePersonalImpl();
    @BeforeEach
    void setUp() {
        conn = MongoConnection.getInstance();

    }

    @AfterEach
    void close(){
        conn.getSalesmenCollection().drop();
    }

    @Test
    void insertSalesMan() {
        testClass.createSalesMan(testPerson);

        // READ (Finding) the stored Documnent
        Document foundDoc = conn.getSalesmenCollection().find(eq("sid",testPerson.getId())).first();
        System.out.println("Printing the object (JSON): " + foundDoc );

        // Assertion
        Integer sid = (Integer) foundDoc.get("sid");
        assertEquals( testPerson.getId() , sid );

        // Expect Exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> testClass.createSalesMan(testPerson));
    }

    @Test
    void deleteSalesMan() {
        testClass.createSalesMan(testPerson);

        // READ (Finding) the stored Documnent
        Document newDocument = conn.getSalesmenCollection().find(eq("sid",testPerson.getId())).first();
        System.out.println("Printing the object (JSON): " + newDocument );

        // Assertion
        Integer sid = (Integer) newDocument.get("sid");
        assertEquals( 90133 , sid );

        testClass.deleteSalesMan(testPerson);

        Document foundDoc = conn.getSalesmenCollection().find(eq("sid",testPerson.getId())).first();

        Assertions.assertEquals(null, foundDoc);

        // Test Exception
        Assertions.assertThrows(NoSuchElementException.class, () -> testClass.deleteSalesMan(wrongPerson));
    }

    @Test
    void updateSalesMan(){
        testClass.createSalesMan(testPerson);

        SalesMan foundPerson = testClass.readSalesMan(testPerson.getId());

        Assertions.assertEquals(testPerson.getId(),foundPerson.getId());

        SalesMan updatedPerson = new SalesMan("Sascha","Domm",testPerson.getId());

        testClass.updateSalesMan(updatedPerson);

        SalesMan foundUpdatedPerson = testClass.readSalesMan(testPerson.getId());

        Assertions.assertEquals(updatedPerson.getLastname(),foundUpdatedPerson.getLastname());
    }

    @Test
    void readSalesMan() {
        testClass.createSalesMan(testPerson);

        SalesMan actPerson = testClass.readSalesMan(testPerson.getId());
        Assertions.assertEquals(testPerson.getId(),actPerson.getId());

        //Throw Exception
        Assertions.assertThrows(NoSuchElementException.class,() -> testClass.readSalesMan(wrongPerson.getId()));
    }

    @Test
    void readAllSalesMen(){
        testClass.createSalesMan(testPerson);

        List<SalesMan> expList = new ArrayList<SalesMan>();
        expList.add(testPerson);

        List<SalesMan> actList = testClass.readAllSalesMen();

        // Assertion
        for (SalesMan man:actList) {
            if(!expList.contains(man)){
                throw new NoSuchElementException();
            }
        }

        testClass.deleteSalesMan(testPerson);

        // Throw Exception
        Assertions.assertThrows(NoSuchElementException.class, () -> testClass.readAllSalesMen());
    }

    @Test
    void addSocialPerformanceRecord(){
        testClass.createSalesMan(testPerson);

        testClass.addSocialPerformanceRecord(testRecord,testPerson);

        SalesMan testObj = testClass.readSalesMan(testPerson.getId());
        System.out.println(testObj);

        Document foundDoc = conn.getSocialPerformanceCollection().find(eq("goalId","90909")).first();
        System.out.println("Printing the object (JSON): " + foundDoc);

        Assertions.assertEquals("90909",foundDoc.get("goalId"));

        // Throw Exception
        Assertions.assertThrows(NoSuchElementException.class,() -> testClass.readSocialPerformanceRecord(wrongPerson));
    }

    @Test
    void deleteSocialPerformanceRecord(){
        testClass.createSalesMan(testPerson);

        testClass.addSocialPerformanceRecord(testRecord,testPerson);

        List<SocialPerformanceRecord> actList = testClass.readSocialPerformanceRecord(testPerson);

        List<SocialPerformanceRecord> expList = new ArrayList<SocialPerformanceRecord>();
        expList.add(testRecord);

        for (SocialPerformanceRecord record:expList) {
            if(!actList.contains(record)){
                throw new NoSuchElementException();
            }
        }

        // Test Deletion
        testClass.deleteSocialPerformanceRecord(testRecord,testPerson);

        // Expect Exception
        Assertions.assertThrows(NoSuchElementException.class, () -> testClass.readSocialPerformanceRecord(testPerson));
    }

    @Test
    void readSocialPerformanceRecord(){
        testClass.createSalesMan(testPerson);

        testClass.addSocialPerformanceRecord(testRecord,testPerson);

        List<SocialPerformanceRecord> actList = testClass.readSocialPerformanceRecord(testPerson);

        List<SocialPerformanceRecord> expList = new ArrayList<SocialPerformanceRecord>();
        expList.add(testRecord);

        for (SocialPerformanceRecord record:actList) {
            if(!expList.contains(record)){
                throw new NoSuchElementException();
            }
        }

        testClass.deleteSocialPerformanceRecord(testRecord,testPerson);

        // Throw Exception
        Assertions.assertThrows(NoSuchElementException.class, () -> testClass.readSocialPerformanceRecord(testPerson));
    }

    @Test
    void readAllSocialPerformanceRecord() {
        testClass.createSalesMan(testPerson);

        testClass.addSocialPerformanceRecord(testRecord,testPerson);

        List<SocialPerformanceRecord> actList = testClass.readAllSocialPerformanceRecord();

        List<SocialPerformanceRecord> expList = new ArrayList<SocialPerformanceRecord>();
        expList.add(testRecord);

        for (SocialPerformanceRecord record:actList) {
            if(!expList.contains(record)){
                throw new NoSuchElementException();
            }
        }

        testClass.deleteSocialPerformanceRecord(testRecord,testPerson);

        // Throw Exception
        Assertions.assertThrows(NoSuchElementException.class, () -> testClass.readAllSocialPerformanceRecord());
    }

    *//*@Test
    void insertSalesManMoreObjectOriented() {
        // CREATE (Storing) the salesman business object
        // Using setter instead
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90444 );

        // ... now storing the object
        salesmen.insertOne(salesMan.toDocument());

        // READ (Finding) the stored Documnent
        // Mapping Document to business object would be fine...
        Document newDocument = this.salesmen.find().first();
        System.out.println("Printing the object (JSON): " + newDocument );

        // Assertion
        Integer sid = (Integer) newDocument.get("sid");
        assertEquals( 90444 , sid );

        // Deletion
        salesmen.drop();
    } */
}

package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCursor;
import de.hbrs.ia.db.MongoConnection;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ManagePersonalImpl implements ManagePersonal{
    private MongoConnection mongo = MongoConnection.getInstance();

    @Override
    public void createSalesMan(SalesMan record) {
        mongo.getSalesmenCollection().insertOne(record.toDocument());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

    }

    @Override
    public SalesMan readSalesMan(int sid) {
        Document result =  mongo.getSalesmenCollection()
            .find(eq("sid", sid)).first();
        if(result == null)
            return null;
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
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        return List.of();
    }
}

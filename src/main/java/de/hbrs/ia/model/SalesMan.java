package de.hbrs.ia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

public class SalesMan {
    private Integer sid;
    private String firstname;
    private String lastname;
    private final List<Integer> goalIDs = new ArrayList<>();

    public SalesMan( Integer sid, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return sid;
    }

    public void setId(Integer sid) {
        this.sid = sid;
    }

    public List<Integer> getGoalIDs(){
        return goalIDs;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("sid" , this.sid);
        document.append("firstname" , this.firstname );
        document.append("lastname" , this.lastname );
        document.append("gids", goalIDs);
        return document;
    }

    public static SalesMan fromDocument(Document doc){
        try {
            SalesMan record = new SalesMan(
                (Integer) doc.get("sid"),
                (String) doc.get("firstname"),
                (String) doc.get("lastname")
            );

            if ( doc.get("gids") != null) {
                List<Integer> goalIDs = ((List<?>) doc.get("gids"))
                    .stream()
                    .filter(Integer.class::isInstance)
                    .map(Integer.class::cast)
                    .collect(Collectors.toList());
                record.getGoalIDs().addAll(goalIDs);
            }
            return record;
        }
        catch (RuntimeException e) {
           throw new IllegalArgumentException("Fehler beim Lesen.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(sid).append(", ");
        sb.append("Vorname: ").append(firstname).append(", ");
        sb.append("Nachname: ").append(lastname).append(", ");
        sb.append("Zugeordnete Ziele: ").append(goalIDs).append(" ");
        return sb.toString();
    }
}

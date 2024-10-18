package de.hbrs.ia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

public class SalesMan {
    private String firstname;
    private String lastname;
    private Integer sid;
    private List<Integer> goalIDs;

    public SalesMan(String firstname, String lastname, Integer sid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;
        this.goalIDs = new ArrayList<>();
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
        org.bson.Document document = new Document();
        document.append("firstname" , this.firstname );
        document.append("lastname" , this.lastname );
        document.append("sid" , this.sid);
        document.append("gids", goalIDs);
        return document;
    }

    public static SalesMan fromDocument(Document doc){
        try{
            List<Integer> goalIDs = ((List<?>) doc.get("goalIDs"))
                .stream()
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .collect(Collectors.toList());

            return new SalesMan(
                (String) doc.get("firstname"),
                (String) doc.get("lastname"),
                (Integer) doc.get("sid")
            );
        }
        catch (RuntimeException e) {
            System.out.println("Fehler beim Deserialisieren.");
            return null;
        }
    }
}

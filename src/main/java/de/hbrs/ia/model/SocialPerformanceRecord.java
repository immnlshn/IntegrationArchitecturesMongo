package de.hbrs.ia.model;

import org.bson.Document;

public class SocialPerformanceRecord {

    private Integer gid;
    private String description;
    private Integer targetValue;
    private Integer actualValue;
    private Integer year;

    public SocialPerformanceRecord(Integer gid, String description, Integer targetValue,
        Integer actualValue, Integer year) {
        this.gid = gid;
        this.description = description;
        this.targetValue = targetValue;
        this.actualValue = actualValue;
        this.year = year;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public void setActualValue(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("gid" , this.gid );
        document.append("description" , this.description );
        document.append("targetValue" , this.targetValue);
        document.append("actualValue" , this.actualValue);
        document.append("year" , this.year);
        return document;
    }

    public static SocialPerformanceRecord fromDocument(Document doc){
        return new SocialPerformanceRecord(
            (Integer) doc.get("gid"),
            (String) doc.get("description"),
            (Integer) doc.get("targetValue"),
            (Integer) doc.get("actualValue"),
            (Integer) doc.get("year")
        );
    }
}

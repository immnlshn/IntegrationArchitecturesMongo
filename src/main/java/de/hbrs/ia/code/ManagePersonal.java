package de.hbrs.ia.code;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Code lines are commented for suppressing compile errors.
 * Are there any CRUD-operations missing?
 */
public interface ManagePersonal {
     public void createSalesMan( SalesMan record ) throws IllegalArgumentException;

     public SalesMan readSalesMan( int sid );

     public void updateSalesMan(SalesMan record) throws NoSuchElementException;

     public void deleteSalesMan(SalesMan record) throws NoSuchElementException;

     public void addSocialPerformanceRecord(SocialPerformanceRecord record , SalesMan salesMan ) throws NoSuchElementException;
    // Remark: an SocialPerformanceRecord corresponds to part B of a bonus sheet

     public List<SalesMan> readAllSalesMen();

     public List<SocialPerformanceRecord> readSocialPerformanceRecord( SalesMan salesMan ) throws NoSuchElementException;
    // Remark: How do you integrate the year?

     public void deleteSocialPerformanceRecord(SocialPerformanceRecord record) throws NoSuchElementException;

}

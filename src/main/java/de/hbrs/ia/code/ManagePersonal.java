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
     void createSalesMan( SalesMan record ) throws IllegalArgumentException;

     SalesMan readSalesMan( int sid );

     void updateSalesMan(SalesMan record) throws NoSuchElementException;

     void deleteSalesMan(SalesMan record) throws NoSuchElementException;

     void addSocialPerformanceRecord(SalesMan salesman , SocialPerformanceRecord record ) throws NoSuchElementException;
    // Remark: an SocialPerformanceRecord corresponds to part B of a bonus sheet

     List<SalesMan> readAllSalesMen();

     List<SocialPerformanceRecord> readSocialPerformanceRecord( SalesMan salesman ) throws NoSuchElementException;
    // Remark: How do you integrate the year?

     SocialPerformanceRecord readSocialPerformanceRecord(int gid) throws NoSuchElementException;

     void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesman) throws NoSuchElementException;

}

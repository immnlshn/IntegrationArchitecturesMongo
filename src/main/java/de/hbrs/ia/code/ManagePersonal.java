package de.hbrs.ia.code;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;

import java.util.List;
import java.util.NoSuchElementException;

// Control Interface to manage the salesman and their social performance records
public interface ManagePersonal {

     // Create a new SalesMan record
     void createSalesMan( SalesMan record ) throws IllegalArgumentException;

     // Read a SalesMan record by its ID
     SalesMan readSalesMan( int sid );

     // Update a SalesMan record
     void updateSalesMan(SalesMan record) throws NoSuchElementException;

     // Delete a SalesMan record
     void deleteSalesMan(SalesMan record) throws NoSuchElementException;

     // Add a new SocialPerformanceRecord to a SalesMan
     void addSocialPerformanceRecord(SalesMan salesman , SocialPerformanceRecord record ) throws NoSuchElementException;

     // Read all Salesman records
     List<SalesMan> readAllSalesMen();

     // Read all goals of a SalesMan
     List<SocialPerformanceRecord> readSocialPerformanceRecord( SalesMan salesman ) throws NoSuchElementException;

     // Read a SocialPerformanceRecord by its ID
     SocialPerformanceRecord readSocialPerformanceRecord(int gid) throws NoSuchElementException;

     // Delete a SocialPerformanceRecord
     void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesman) throws NoSuchElementException;

}
